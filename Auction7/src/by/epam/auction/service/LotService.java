package by.epam.auction.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.ParsingValues;
import by.epam.auction.dao.auction.AuctionDAOImpl;
import by.epam.auction.dao.bid.BidDAOImpl;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.lot.LotDAOImpl;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Auction;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.domain.Bid;
import by.epam.auction.domain.Lot;
import by.epam.auction.domain.User;
import by.epam.auction.service.exception.ServiceException;
import by.epam.auction.service.util.ImageSaver;

public class LotService {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Find up to required number of lots.
	 * 
	 * @param number
	 * @return
	 * @throws ServiceException
	 */
	public Optional<Set<Lot>> findLotLimit(int from, int number) throws ServiceException {
		LOG.log(Level.DEBUG, "Service looking for " + number + " lot entities");
		Optional<Set<Lot>> optionalLotSet = Optional.empty();

		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLotSet = Optional.of(lotDAO.takeLotLimit(from, number));
			LOG.log(Level.DEBUG, "Service returns " + optionalLotSet.get().size() + " lot entities");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}

		return optionalLotSet;
	}

	public Optional<Set<Lot>> takeLotSet(long id, long l, int quantity) throws ServiceException {
		LOG.log(Level.DEBUG, "Service looking for lots in auction: " + id);
		Optional<Set<Lot>> optionalLotSet = Optional.empty();

		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLotSet = Optional.of(lotDAO.takeAuctionLotLimit(id, l, quantity));
			LOG.log(Level.DEBUG, "Service returns " + optionalLotSet.get().size() + " lot entities");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}

		return optionalLotSet;
	}

	/**
	 * Find lot by its Id.
	 * 
	 * @param lotId
	 * @return
	 * @throws ServiceException
	 */
	public Optional<Lot> findLotById(long lotId) throws ServiceException {
		LOG.log(Level.DEBUG, "Requested lot with id =" + lotId);
		Optional<Lot> optionalLot = Optional.empty();
		try(LotDAOImpl lotDAO = new LotDAOImpl()){
			optionalLot = lotDAO.findLotById(lotId);
		} catch (DAOException | ConnectionPoolException e) {
			throw new ServiceException(e);
		}
		return optionalLot;
	}

	public User placeBid(Long lotId, BigDecimal amount, Long userId) throws ServiceException {
		LOG.log(Level.DEBUG, "Service recive bid for lot with id: " + lotId + ", with price:" + amount
				+ ", by user with id:" + userId);
		User user = null;
		Optional<Lot> optionalLot = Optional.empty();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLot = lotDAO.findLotById(lotId);
			LOG.log(Level.DEBUG, "Lot found " + optionalLot.get());
		} catch (DAOException | ConnectionPoolException e) {
			LOG.log(Level.ERROR, "Unable to find lot with id =" + lotId);
			throw new ServiceException(e);
		}

		Optional<AuctionType> type = Optional.empty();
		Optional<Auction> optionalAuction = Optional.empty();
		if (optionalLot.isPresent()) {
			try (AuctionDAOImpl auctionDAO = new AuctionDAOImpl()) {
				optionalAuction = auctionDAO.findAuctionById(optionalLot.get().getAuctionId());
			} catch (ConnectionPoolException | DAOException e) {
				throw new ServiceException(e);
			}
			if (optionalAuction.isPresent()) {
				LOG.log(Level.DEBUG, "Service return auction: " + optionalAuction.get());
				type = Optional.of(optionalAuction.get().getType());
				LOG.log(Level.DEBUG, "Auction type found: " + type);
			} else {
				throw new ServiceException("Auction not found for lot:" + optionalLot.get());
			}
		}

		if (type.isPresent() && optionalAuction.get().getStartTime().isBefore(LocalDateTime.now())
				&& optionalAuction.get().getFinishTime().isAfter(LocalDateTime.now())
				&& optionalLot.get().isApproved()) {
			try (BidDAOImpl bidDAO = new BidDAOImpl()) {
				bidDAO.startTransaction();
				Optional<Bid> previousBid = Optional.empty();
				Optional<User> optionalUser = bidDAO.findUserById(userId);
				if (optionalUser.isPresent() && !optionalUser.get().isBlocked()) {
					LOG.log(Level.DEBUG, "optionalUser found: " + optionalUser.toString());
					user = optionalUser.get();
					if (type.get().equals(AuctionType.DIRECT)) {
						previousBid = bidDAO.findLotMaxBid(lotId);
					} else if (type.get().equals(AuctionType.REVERSE)) {
						previousBid = bidDAO.findLotMinBid(lotId);
					}
					if (previousBid.isPresent()) {
						if ((type.get().equals(AuctionType.DIRECT)
								&& amount.compareTo(previousBid.get().getAmount()) > 0
								&& amount.compareTo(user.getMoney()) <= 0)) {
							Bid bid = new Bid();
							bid.setAmount(amount);
							bid.setBidderId(userId);
							bid.setLotId(lotId);
							boolean isBidPlaced = bidDAO.createBid(bid);
							user.setMoney(user.getMoney().add(amount.negate()));
							user.setFrozenMoney(user.getFrozenMoney().add(amount));
							boolean isUserUpdated = user.getMoney().compareTo(BigDecimal.ZERO) >= 0
									&& bidDAO.updateUser(user);
							Optional<User> previousBidder = bidDAO.findUserById(previousBid.get().getBidderId());
							if (previousBidder.isPresent()) {
								User previous = previousBidder.get();
								previous.setFrozenMoney(previous.getFrozenMoney().add(previousBid.get().getAmount().negate()));
								previous.setMoney(previous.getMoney().add(previousBid.get().getAmount()));
								if (isBidPlaced && isUserUpdated && bidDAO.updateUser(previous)) {
									Optional<User> optionalUserNew = bidDAO.findUserById(user.getId());
									if(optionalUserNew.isPresent()) {
										user = optionalUserNew.get();
										LOG.log(Level.DEBUG, "Direct bid placed.");
									} else {
										bidDAO.rollbackTransaction();
									}
								} else {
									bidDAO.rollbackTransaction();
								}
							}
						} else if (type.get().equals(AuctionType.REVERSE)
								&& amount.compareTo(previousBid.get().getAmount()) < 0) {
							// TO DO - get lotOwner by lotId and update him
							//optionalLot.get().getAuctionId();
							LOG.log(Level.DEBUG, "Reverse bid placed.");
						} 
					}
				}
				bidDAO.finishTransaction();
			} catch (DAOException | ConnectionPoolException e) {
				throw new ServiceException(e);
			}
		} else {
			LOG.log(Level.DEBUG, "Bid cannot be placed");
		}

		return user;
	}

	public Optional<Set<Lot>> findProposedLotSet() throws ServiceException {
		Optional<Set<Lot>> proposedLotSet = Optional.empty();

		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			proposedLotSet = Optional.ofNullable(lotDAO.takeProposedLotSet());
		} catch (SQLException | ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}

		return proposedLotSet;
	}

	public Map<Long, String> takeCategoryMap() throws ServiceException {
		Map<Long, String> categoryMap = new HashMap<>();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			categoryMap = lotDAO.takeCategoryPathMap();
		} catch (ConnectionPoolException | SQLException | DAOException e) {
			throw new ServiceException(e);
		}
		return categoryMap;
	}

	public Long proposeLot(Long userId, Long auctionId, String lotName, String lotDescription, BigDecimal amount,
			Long categoryId, String path, Part file1, Part file2, Part file3) throws ServiceException {
		Long lotId = null;
		Optional<User> optionalUser = Optional.empty();
		try (LotDAOImpl lotDao = new LotDAOImpl()) {
			lotDao.startTransaction();
			optionalUser = lotDao.findUserById(userId);
			if (optionalUser.isPresent() && !optionalUser.get().isBlocked()) {
				Optional<Auction> optionalAuction = Optional.empty();
				optionalAuction = lotDao.findAuctionById(auctionId);
				if (optionalAuction.isPresent() && optionalAuction.get().getStartTime().isAfter(LocalDateTime.now())) {
					Map<Long, String> categoryMap = lotDao.takeCategoryPathMap();
					if (categoryMap != null && categoryMap.keySet().contains(categoryId)) {

						String fileName1 = ParsingValues.DEFAULT_LOT_IMG;
						String fileName2 = ParsingValues.DEFAULT_LOT_IMG;
						String fileName3 = ParsingValues.DEFAULT_LOT_IMG;
						if (file1 != null && file1.getSize() != 0) {
							fileName1 = new ImageSaver().writeImg(file1, path);
						}
						if (file2 != null && file2.getSize() != 0) {
							fileName1 = new ImageSaver().writeImg(file2, path);
						}
						if (file3 != null && file3.getSize() != 0) {
							fileName1 = new ImageSaver().writeImg(file3, path);
						}

						lotId = lotDao.proposeLot(userId, auctionId, lotName, lotDescription, amount, categoryId,
								fileName1, fileName2, fileName3);
						LOG.log(Level.DEBUG, "Lot was created with Id = " + lotId);
					}
				}
			}
			lotDao.finishTransaction();
		} catch (ConnectionPoolException | DAOException | SQLException e) {
			throw new ServiceException(e);
		}
		return lotId;
	}

	public boolean approveLot(Long lotId) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}
}

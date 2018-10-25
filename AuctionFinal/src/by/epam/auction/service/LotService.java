package by.epam.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.constant.ParsingValues;
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

/**
 * The Class LotService.
 */
public class LotService {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Find up to required number of lots.
	 *
	 * @param startIndex the start index
	 * @param maxQuantity the max quantity
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	public Optional<Set<Lot>> findLotLimit(int startIndex, int maxQuantity) throws ServiceException {
		LOG.log(Level.DEBUG, "Start index:" + startIndex + " Quantity:" + maxQuantity);
		Optional<Set<Lot>> optionalLotSet = Optional.empty();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLotSet = Optional.of(lotDAO.takeLotLimit(startIndex, maxQuantity));
			LOG.log(Level.DEBUG, "Return " + optionalLotSet.get().size() + " lot entities");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return optionalLotSet;
	}

	/**
	 * Take lot set.
	 *
	 * @param id the id
	 * @param startIndex the start index
	 * @param maxQuantity the max quantity
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	public Optional<Set<Lot>> takeLotSet(long id, long startIndex, int maxQuantity) throws ServiceException {
		LOG.log(Level.DEBUG, "Request for lot set in auction: " + id + " starting from index:" + startIndex
				+ " with quantity of up to:" + maxQuantity);
		Optional<Set<Lot>> optionalLotSet = Optional.empty();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLotSet = Optional.of(lotDAO.takeAuctionLotLimit(id, startIndex, maxQuantity));
			LOG.log(Level.DEBUG, "Returns " + optionalLotSet.get().size() + " lot entities");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return optionalLotSet;
	}

	/**
	 * Find lot by id.
	 *
	 * @param lotId the lot id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	public Optional<Lot> findLotById(long lotId) throws ServiceException {
		LOG.log(Level.DEBUG, "Requested lot with id =" + lotId);
		Optional<Lot> optionalLot = Optional.empty();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			optionalLot = lotDAO.findLotById(lotId);
			LOG.log(Level.DEBUG, "Lot " + (optionalLot.isPresent() ? "found" : "not found"));
		} catch (DAOException | ConnectionPoolException e) {
			throw new ServiceException(e);
		}
		return optionalLot;
	}

	/**
	 * Place bid.
	 *
	 * @param lotId the lot id
	 * @param amount the amount
	 * @param bidderId the bidder id
	 * @return the user
	 * @throws ServiceException the service exception
	 */
	public User placeBid(Long lotId, BigDecimal amount, Long bidderId) throws ServiceException {
		LOG.log(Level.DEBUG, "Service recive bid for lot with id: " + lotId + ", with price:" + amount
				+ ", by user with id:" + bidderId);
		if (lotId == null || amount == null || bidderId == null) {
			throw new ServiceException("Null parameter provided");
		}
		
		User newBidderState = null;
		try (BidDAOImpl bidDAO = new BidDAOImpl()) {
			bidDAO.startTransaction();
			Optional<User> userOptional = bidDAO.findUserById(bidderId);
			if(!userOptional.isPresent()) {
				throw new ServiceException("Bidder with id = " + bidderId + " not found");
			}
			User bidder = userOptional.get();
			
			Optional<Lot> lotOptional = findLotById(lotId);
			if (!lotOptional.isPresent()) {
				throw new ServiceException("Lot with id = " + lotId + " not found");
			}
			Lot lot = lotOptional.get();
			
			Optional<User> previousBidderOptional = bidDAO.findUserById(lot.getBidderId());		
			
			Optional<User> ownerOptional = bidDAO.findUserById(lot.getOwnerId());
			if(!userOptional.isPresent()) {
				throw new ServiceException("Lot owner with id = " + bidderId + " not found");
			}
			User owner = ownerOptional.get();
				
			if(checkBidValidity(lot, bidder, owner, amount)) {
				LOG.log(Level.TRACE, "Perform bid on " + lot.getAuctionType() + " auction");
				if(lot.getAuctionType() == AuctionType.DIRECT) {
					bidder.setFrozenMoney(bidder.getFrozenMoney().add(amount));
					bidder.setMoney(bidder.getMoney().subtract(amount));
					bidDAO.updateUser(bidder);
					LOG.log(Level.TRACE, "Bid amount on users account had been frozen.");
					
					if(previousBidderOptional.isPresent()) {
						LOG.log(Level.TRACE, "Previous bidder found.");
						User previousBidder = previousBidderOptional.get();
						previousBidder.setMoney(previousBidder.getMoney().add(lot.getBidPrice()));
						previousBidder.setFrozenMoney(previousBidder.getFrozenMoney().subtract(lot.getBidPrice()));
						bidDAO.updateUser(previousBidder);
						LOG.log(Level.TRACE, "Previous bidder's bid amount returned on his account.");
					}
				} else {
					owner.setFrozenMoney(owner.getFrozenMoney().subtract(lot.getBidPrice().subtract(amount)));
					owner.setMoney(owner.getMoney().add(lot.getBidPrice().subtract(amount)));
					bidDAO.updateUser(owner);
				}
				Bid bid = new Bid();
				LOG.log(Level.TRACE, "Place bid");
				bid.setAmount(amount);
				bid.setBidderId(bidder.getId());
				bid.setLotId(lot.getId());
				bidDAO.createBid(bid);
				LOG.log(Level.TRACE, "Bid was placed");
				newBidderState = bidder;
				bidDAO.finishTransaction();
			}
				
			} catch (DAOException | ConnectionPoolException e) {
				throw new ServiceException(e);
			}

		return newBidderState;
	}
	
	/**
	 * Check bid validity.
	 *
	 * @param lot the lot
	 * @param bidder the bidder
	 * @param owner the owner
	 * @param amount the amount
	 * @return true, if successful
	 */
	private boolean checkBidValidity(Lot lot, User bidder, User owner, BigDecimal amount) {
		boolean isValidBid = lot != null
				&& bidder!= null
				&& owner != null
				&& amount != null
				&& isNotSameUser(bidder, owner)
				&& isNotPreviousBidder(lot, bidder)
				&& isActiveLot(lot)
				&& lot.getAuctionType() != null
				&& lot.getBidPrice() != null
				&& ( isSufficientDirectAuctionBid(lot, bidder, amount)
					||isSufficientReverseAuctionBid(lot, owner, amount));
			LOG.log(Level.TRACE, "This bid " + (isValidBid?"is":"isn't") + " valid");
		return isValidBid;
	}

	/**
	 * Checks if is not same user.
	 *
	 * @param bidder the bidder
	 * @param owner the owner
	 * @return true, if is not same user
	 */
	private boolean isNotSameUser(User bidder, User owner) {
		boolean isNotSameUser =  bidder!= null
				&& owner != null
				&& bidder.getId() != null
				&& owner.getId() != null
				&& bidder.getId() != owner.getId();
			LOG.log(Level.TRACE, "This " + (!isNotSameUser?"is":"isn't") + " same user");
		return isNotSameUser;
	}
	
	/**
	 * Checks if is not previous bidder.
	 *
	 * @param lot the lot
	 * @param bidder the bidder
	 * @return true, if is not previous bidder
	 */
	private boolean isNotPreviousBidder(Lot lot, User bidder) {
		boolean isNotPreviousBidder = lot != null
				&& bidder!= null
				&& lot.getBidderId() != null
				&& lot.getBidderId() != bidder.getId();
			LOG.log(Level.TRACE, "This " + (!isNotPreviousBidder?"is":"isn't") + " previous bidder user");
		return isNotPreviousBidder;
	}
	
	/**
	 * Checks if is active lot.
	 *
	 * @param lot the lot
	 * @return true, if is active lot
	 */
	private boolean isActiveLot(Lot lot) {
		boolean isActiveLot = lot != null
				&& lot.isApproved() 
				&& lot.getStartTime() != null 
				&& lot.getStartTime().isBefore(LocalDateTime.now())
				&& lot.getFinishTime() != null 
				&& lot.getFinishTime().isAfter(LocalDateTime.now())
				&& !lot.isOwnerIsBlocked();
			LOG.log(Level.TRACE, "This lot " + (isActiveLot?"is":"isn't") + " active");
		return isActiveLot;
	}
	
	/**
	 * Checks if is sufficient direct auction bid.
	 *
	 * @param lot the lot
	 * @param bidder the bidder
	 * @param amount the amount
	 * @return true, if is sufficient direct auction bid
	 */
	private boolean isSufficientDirectAuctionBid(Lot lot, User bidder, BigDecimal amount) {
		boolean isSufficientBid = lot.getAuctionType() == AuctionType.DIRECT
				&& bidder.getMoney() != null
				&& bidder.getMoney().compareTo(amount)>=0
				&& amount.compareTo(lot.getBidPrice())>0;
			LOG.log(Level.TRACE, "This bid " + (isSufficientBid?"is":"isn't") + " sufficient for direct auction");
		return isSufficientBid;
	}
	
	/**
	 * Checks if is sufficient reverse auction bid.
	 *
	 * @param lot the lot
	 * @param owner the owner
	 * @param amount the amount
	 * @return true, if is sufficient reverse auction bid
	 */
	private boolean isSufficientReverseAuctionBid(Lot lot, User owner, BigDecimal amount) {
		boolean isSufficientBid = lot.getAuctionType() == AuctionType.REVERSE
				&& owner.getMoney() != null
				&& owner.getMoney().add(lot.getBidPrice()).compareTo(amount)>=0
				&& amount.compareTo(lot.getBidPrice()) < 0;
			LOG.log(Level.TRACE, "This bid " + (isSufficientBid?"is":"isn't") + " sufficient for reverse auction");
		return isSufficientBid;
	}

	/**
	 * Find proposed lot set.
	 *
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	public Optional<Set<Lot>> findProposedLotSet() throws ServiceException {
		Optional<Set<Lot>> proposedLotSet = Optional.empty();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			proposedLotSet = Optional.ofNullable(lotDAO.takeProposedLotSet());
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return proposedLotSet;
	}

	/**
	 * Take category map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	public Map<Long, String> takeCategoryMap() throws ServiceException {
		Map<Long, String> categoryMap = new HashMap<>();
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			categoryMap = lotDAO.takeCategoryPathMap();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return categoryMap;
	}

	/**
	 * Propose lot.
	 *
	 * @param ownerId the owner id
	 * @param auctionId the auction id
	 * @param lotName the lot name
	 * @param lotDescription the lot description
	 * @param amount the amount
	 * @param categoryId the category id
	 * @param path the path
	 * @param pictureSet the picture set
	 * @return the long
	 * @throws ServiceException the service exception
	 */
	public Long proposeLot(Long ownerId, Long auctionId, String lotName, String lotDescription, BigDecimal amount,
			Long categoryId, String path, List<Part> pictureSet) throws ServiceException {
		Long lotId = null;
		Optional<User> optionalUser = Optional.empty();
		try (BidDAOImpl bidDAO = new BidDAOImpl()) {
			bidDAO.startTransaction();
			optionalUser = bidDAO.findUserById(ownerId);
			if (optionalUser.isPresent() && !optionalUser.get().isBlocked()) {
				LOG.log(Level.DEBUG, "Owner with active account found");
				Optional<Auction> optionalAuction = Optional.empty();
				optionalAuction = bidDAO.findAuctionById(auctionId);
				if (optionalAuction.isPresent() && optionalAuction.get().getFinishTime().isAfter(LocalDateTime.now())) {
					LOG.log(Level.DEBUG, "Active auction found");
					Map<Long, String> categoryMap = bidDAO.takeCategoryPathMap();
					if (categoryMap != null && categoryMap.keySet().contains(categoryId)) {
						LOG.log(Level.DEBUG, "Id of proposed category is present");
						List<String> pictureNameSet = new ArrayList<>();
						for (Part picture : pictureSet) {
							pictureNameSet.add(new ImageSaver().writeImg(picture, path));
						}
						if (pictureNameSet.size() == 0) {
							pictureNameSet.add(ParsingValues.DEFAULT_LOT_IMG);
						}
						lotId = bidDAO.proposeLot(lotName, lotDescription, categoryId, ownerId, auctionId);
						boolean isPicturesInserted = false;
						if(lotId != null) {
							isPicturesInserted = bidDAO.insertPictures(pictureNameSet, lotId);
						}
						if(isPicturesInserted) {
							Bid bid = new Bid();
							bid.setAmount(amount);
							bid.setLotId(lotId);
							bid.setBidderId(ownerId);
							bidDAO.createBid(bid);
						}
						LOG.log(Level.DEBUG, "Lot was created with Id = " + lotId);
					} else {
						LOG.log(Level.DEBUG, "Id of proposed category not found");
					}
				} else {
					LOG.log(Level.DEBUG, "Active auction not found");
				}
			} else {
				LOG.log(Level.DEBUG, "Active owner didn't exists");
			}
			bidDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return lotId;
	}

	/**
	 * Approve lot.
	 *
	 * @param lotId the lot id
	 * @return true, if successful
	 * @throws ServiceException the service exception
	 */
	public boolean approveLot(Long lotId) throws ServiceException {
		boolean isLotApproved = false;
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			isLotApproved = lotDAO.approveLot(lotId);
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return isLotApproved;
	}

	/**
	 * Un approve lot.
	 *
	 * @param lotId the lot id
	 * @return true, if successful
	 * @throws ServiceException the service exception
	 */
	public boolean unApproveLot(Long lotId) throws ServiceException {
		boolean isLotUnApproved = false;
		try (LotDAOImpl lotDAO = new LotDAOImpl()) {
			isLotUnApproved = lotDAO.unApproveLot(lotId);
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return isLotUnApproved;
	}
}

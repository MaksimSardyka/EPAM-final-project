package by.epam.auction.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.bid.BidDAO;
import by.epam.auction.dao.bid.BidDAOImpl;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.lot.LotDAO;
import by.epam.auction.dao.lot.LotDAOImpl;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.domain.Bid;
import by.epam.auction.domain.Lot;
import by.epam.auction.service.exception.ServiceException;

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
        
        try (LotDAO lotDAO = new LotDAOImpl()) {
            optionalLotSet = Optional.of(lotDAO.getLotLimit(from, number));
            LOG.log(Level.DEBUG, "Service returns " + optionalLotSet.get().size() + " lot entities");
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }

        return optionalLotSet;
    }
    
    public Optional<Set<Lot>> takeLotSet(long id, int from, int quantity) throws ServiceException {
        LOG.log(Level.DEBUG, "Service looking for lots in auction: " + id);
        Optional<Set<Lot>> optionalLotSet = Optional.empty();
        
        try (LotDAO lotDAO = new LotDAOImpl()) {
            optionalLotSet = Optional.of(lotDAO.getAuctionLotLimit(id, from, quantity));
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
        LOG.log(Level.DEBUG, "Service looking for lot with id=" + lotId);

        Optional<Lot> optionalLot = Optional.empty();
        try (LotDAO lotDAO = new LotDAOImpl()) {
            optionalLot = lotDAO.findEntityById(lotId);
        } catch (ConnectionPoolException | DAOException e) {
            LOG.log(Level.ERROR, "Cant find entity for id = " + lotId);
            throw new ServiceException(e);
        }
        
        Optional<AuctionType> type = Optional.empty();
        if (optionalLot.isPresent()) {
            type = Optional.of(optionalLot.get().getAuction().getType());
            LOG.log(Level.ERROR, "Auction type defined:" + type.get().name());
        }

        Optional<BigDecimal> optionalPrice = Optional.empty();
        if(type.isPresent()) {
            try (BidDAO bidDAO = new BidDAOImpl()) {
            	if(type.get().equals(AuctionType.DIRECT)) {
            		optionalPrice = bidDAO.findLotMaxBid(lotId);
            	} else if(type.get().equals(AuctionType.REVERSE)) {
            		optionalPrice = bidDAO.findLotMinBid(lotId);
            	}
            } catch (DAOException | ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }

        if (optionalPrice.isPresent() && optionalPrice.isPresent()) {
            optionalLot.get().setPrice(optionalPrice.get());
        } else {
            optionalLot = Optional.empty();
        }

        return optionalLot;
    }

    public boolean placeBid(Long lotId, BigDecimal amount, Long userId) throws ServiceException {
        LOG.log(Level.DEBUG, "Service recive bid for lot with id: " + lotId + ", with price:" + amount + ", by user with id:" + userId);
        
        Optional<Lot> optionalLot = Optional.empty();
        try (LotDAO lotDAO = new LotDAOImpl()) {
            optionalLot = lotDAO.findEntityById(lotId);
            LOG.log(Level.DEBUG, "Lot found " + optionalLot.get());
        } catch (DAOException | ConnectionPoolException e1) {
            LOG.log(Level.ERROR, "Unable to find lot with id =" + lotId);
            throw new ServiceException(e1);
        }

        Optional<AuctionType> type = Optional.empty();
        if (optionalLot.isPresent()) {
            type = Optional.of(optionalLot.get().getAuction().getType());
            LOG.log(Level.ERROR, "Auction type defined:" + type.get().name());
        }

        Optional<BigDecimal> price = Optional.empty();
        if(type.isPresent()) {
            try (BidDAO bidDAO = new BidDAOImpl()) {
            	if(type.get().equals(AuctionType.DIRECT)) {
            		price = bidDAO.findLotMaxBid(lotId);
            	} else if(type.get().equals(AuctionType.REVERSE)) {
            		price = bidDAO.findLotMinBid(lotId);
            	}
            } catch (DAOException | ConnectionPoolException e) {
                throw new ServiceException(e);
            }
        }
        
        boolean bidPlaced = false;
        if (price.isPresent()) {
        	if((type.get().equals(AuctionType.DIRECT) && amount.compareTo(price.get()) == 1) 
        			|| (type.get().equals(AuctionType.REVERSE) && amount.compareTo(price.get())==-1)) {
                try (BidDAO bidDAO = new BidDAOImpl()) {
                    Bid bid = new Bid();
                    bid.setAmount(amount);
                    bid.setBidderId(userId);
                    bid.setLotId(lotId);
                    bidDAO.create(bid);
                    LOG.log(Level.DEBUG, "Bid placed.");
                } catch (DAOException | ConnectionPoolException e) {
                    throw new ServiceException(e);
                }	
            } else {
                LOG.log(Level.ERROR, "Non-valid price provided.");
            }
        }
        
        return bidPlaced;
    }
}

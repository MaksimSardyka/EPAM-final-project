package by.epam.auction.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.auction.AuctionDAOImpl;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Auction;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.service.exception.ServiceException;

public class AuctionService{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    /**
     * Find all lots.
     * @return
     * @throws ServiceException
     */
    public Optional<Set<Auction>> getAllAuction() throws ServiceException{
        LOG.log(Level.DEBUG, "Service looking for lot entities");
        Optional<Set<Auction>> optionalAuctionSet = Optional.empty();
        try (AuctionDAOImpl auctionDAO = new AuctionDAOImpl()) {
            optionalAuctionSet = Optional.of(auctionDAO.findAllAuction());
            LOG.log(Level.DEBUG, "Service returns optional auction entities");
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return optionalAuctionSet;
    }
    
    public boolean createAuction(LocalDateTime startTime, LocalDateTime finishTime, String description, AuctionType type) throws ServiceException{
        LOG.log(Level.DEBUG, "Service creates lot:");
        boolean isAuctionCreated = false;
        
        try (AuctionDAOImpl auctionDAO = new AuctionDAOImpl()) {
        	isAuctionCreated = auctionDAO.createAuction(startTime, finishTime, description, type);
            LOG.log(Level.DEBUG, "auction was created");
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return isAuctionCreated;
    }

	public Optional<Auction> findAuctionById(Long auctionId) throws ServiceException {
		Optional<Auction> auctionOptional = Optional.empty();
		
        try (AuctionDAOImpl auctionDAO = new AuctionDAOImpl()) {
        	auctionOptional = auctionDAO.findAuctionById(auctionId);
            LOG.log(Level.DEBUG, auctionOptional);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        
		return auctionOptional;
	}
}

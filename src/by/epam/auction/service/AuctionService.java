package by.epam.auction.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.auction.AuctionDAO;
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
    public Optional<Set<Auction>> getAll() throws ServiceException{
        LOG.log(Level.DEBUG, "Service looking for lot entities");
        Optional<Set<Auction>> optionalAuctionSet = Optional.empty();
        try (AuctionDAO auctionDAO = new AuctionDAOImpl()) {
            optionalAuctionSet = Optional.of(auctionDAO.findAll());
            LOG.log(Level.DEBUG, "Service returns " + optionalAuctionSet.get().size() + " auction entities");
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
        return optionalAuctionSet;
    }
    
    public boolean createAuction(LocalDateTime startTime, LocalDateTime finishTime, String description, AuctionType type) throws ServiceException{
        LOG.log(Level.DEBUG, "Service creates lot:");
        boolean result = false;
        
        Auction auction = new Auction();
        auction.setStartTime(startTime);
        auction.setFinishTime(finishTime);
        auction.setDescription(description);
        auction.setType(type);
        
        try (AuctionDAO auctionDAO = new AuctionDAOImpl()) {
            result = auctionDAO.create(auction);
            LOG.log(Level.DEBUG, auction + "was created");
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}

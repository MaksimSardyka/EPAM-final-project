package by.epam.auction.dao.bid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.user.UserDAOImpl;
import by.epam.auction.domain.Bid;

public class BidDAOImpl extends UserDAOImpl {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    private static final String CREATE_BID = "INSERT INTO `auction`.`bid` (`price`, `user_id`, `lot_id`) VALUES (?, ?, ?);"; 

    public BidDAOImpl() throws ConnectionPoolException {
        super();
    }

    
    public Set<Bid> findAllBid() throws DAOException {
        throw new DAOException("Operation not supported");
    }

   
    public Optional<Bid> findBidById(long id) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    
    public boolean deleteBid(int id) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    
    public boolean deleteBid(Bid entity) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    
    public boolean createBid(Bid bid) throws DAOException {
        int resultSet;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(CREATE_BID)) {
            preparedStatement.setBigDecimal(1, bid.getAmount());
            preparedStatement.setLong(2, bid.getBidderId());
            preparedStatement.setLong(3, bid.getLotId());
            resultSet = preparedStatement.executeUpdate();
            LOG.log(Level.DEBUG, "Bid placed: " + (resultSet == 1));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return resultSet == 1;
    }

    
    public boolean updateBid(Bid entity) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    private static final String FIND_MAX_BID = "SELECT id, price, user_id, lot_id FROM auction.bid WHERE price = (SELECT MAX(price) AS p FROM auction.bid WHERE bid.lot_id = ?);";
    
    public Optional<Bid> findLotMaxBid(long lotId) throws DAOException {
        Optional<Bid> maxBid = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MAX_BID)) {
            preparedStatement.setLong(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
            	Bid bid = new Bid();
            	bid.setId(resultSet.getLong("id"));
            	bid.setAmount(resultSet.getBigDecimal("price"));
            	bid.setBidderId(resultSet.getLong("user_id"));
            	bid.setLotId(resultSet.getLong("lot_id"));
                maxBid = Optional.of(bid);
                LOG.log(Level.DEBUG, "Max bid found: " + bid);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return maxBid;
    }
    
    private static final String FIND_MIN_BID ="SELECT id, price, user_id, lot_id FROM auction.bid WHERE price = (SELECT MIN(price) AS p FROM auction.bid WHERE bid.lot_id = ?);";
    
    public Optional<Bid> findLotMinBid(long lotId) throws DAOException {
    	Optional<Bid> minBid = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MIN_BID)) {
            preparedStatement.setLong(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
            	Bid bid = new Bid();
            	bid.setId(resultSet.getLong("id"));
            	bid.setAmount(resultSet.getBigDecimal("price"));
            	bid.setBidderId(resultSet.getLong("user_id"));
            	bid.setLotId(resultSet.getLong("lot_id"));
                minBid = Optional.of(bid);
                LOG.log(Level.DEBUG, "Min bid found: " + bid);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return minBid;
    }
    
    private static final String FIND_MAX_BID_PRICE = "SELECT MAX(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    
    public Optional<BigDecimal> findLotMaxBidPrice(long lotId) throws DAOException {
        Optional<BigDecimal> price = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MAX_BID_PRICE)) {
            preparedStatement.setLong(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                price = Optional.of(resultSet.getBigDecimal("price"));
                LOG.log(Level.DEBUG, "Max bid for lot with id: " + lotId + " found:" + price.get());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return price;
    }
    
    private static final String FIND_MIN_BID_PRICE = "SELECT MIN(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    
    public Optional<BigDecimal> findLotMinBidPrice(long lotId) throws DAOException {
        Optional<BigDecimal> price = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MIN_BID_PRICE)) {
            preparedStatement.setLong(1, lotId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                price = Optional.of(resultSet.getBigDecimal("price"));
                LOG.log(Level.DEBUG, "Min bid for lot with id: " + lotId + " found:" + price.get());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return price;
    }
}

package by.epam.auction.dao.bid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.lot.LotDAOImpl;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Bid;

/**
 * The Class BidDAOImpl.
 */
public class BidDAOImpl extends LotDAOImpl {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    /** The Constant CREATE_BID. */
    private static final String CREATE_BID = "INSERT INTO `auction`.`bid` (`price`, `user_id`, `lot_id`) VALUES (?, ?, ?);"; 
    
    /** The Constant FIND_MAX_BID. */
    private static final String FIND_MAX_BID = "SELECT id, price, user_id, lot_id FROM auction.bid WHERE price = (SELECT MAX(price) AS p FROM auction.bid WHERE bid.lot_id = ?);";
    
    /** The Constant FIND_MIN_BID. */
    private static final String FIND_MIN_BID ="SELECT id, price, user_id, lot_id FROM auction.bid WHERE price = (SELECT MIN(price) AS p FROM auction.bid WHERE bid.lot_id = ?);";
    
    /** The Constant FIND_MAX_BID_PRICE. */
    private static final String FIND_MAX_BID_PRICE = "SELECT MAX(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    
    /** The Constant FIND_MIN_BID_PRICE. */
    private static final String FIND_MIN_BID_PRICE = "SELECT MIN(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    
    /**
     * Instantiates a new bid DAO impl.
     *
     * @throws ConnectionPoolException the connection pool exception
     */
    public BidDAOImpl() throws ConnectionPoolException {
        super();
    }
    
    /**
     * Creates the bid.
     *
     * @param bid the bid
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
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
    
    /**
     * Find lot max bid.
     *
     * @param lotId the lot id
     * @return the optional
     * @throws DAOException the DAO exception
     */
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
    
    /**
     * Find lot min bid.
     *
     * @param lotId the lot id
     * @return the optional
     * @throws DAOException the DAO exception
     */
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
    
    /**
     * Find lot max bid price.
     *
     * @param lotId the lot id
     * @return the optional
     * @throws DAOException the DAO exception
     */
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
    
    /**
     * Find lot min bid price.
     *
     * @param lotId the lot id
     * @return the optional
     * @throws DAOException the DAO exception
     */
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

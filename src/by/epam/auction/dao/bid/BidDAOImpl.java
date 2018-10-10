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

import by.epam.auction.dao.BaseDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Bid;

public class BidDAOImpl extends BaseDAO<Bid> implements BidDAO{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();
    
    private static final String CREATE_BID = "INSERT INTO `auction`.`bid` (`price`, `user_id`, `lot_id`) VALUES (?, ?, ?);"; 

    public BidDAOImpl() throws ConnectionPoolException {
        super();
    }

    @Override
    public Set<Bid> findAll() throws DAOException {
        throw new DAOException("Operation not supported");
    }

    @Override
    public Optional<Bid> findEntityById(long id) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    @Override
    public boolean delete(int id) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    @Override
    public boolean delete(Bid entity) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    @Override
    public boolean create(Bid bid) throws DAOException {
        int resultSet;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(CREATE_BID)) {
            preparedStatement.setBigDecimal(1, bid.getAmount());
            preparedStatement.setLong(2, bid.getBidderId());
            preparedStatement.setLong(3, bid.getLotId());
            resultSet = preparedStatement.executeUpdate();
            LOG.log(Level.DEBUG, "Bid placed");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return resultSet == 1;
    }

    @Override
    public boolean update(Bid entity) throws DAOException {
        throw new DAOException("Operation not supported");
    }

    String FIND_MAX_BID = "SELECT MAX(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    @Override
    public Optional<BigDecimal> findLotMaxBid(long lotId) throws DAOException {
        Optional<BigDecimal> price = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MAX_BID)) {
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
    
    String FIND_MIN_BID = "SELECT MIN(price) AS price FROM auction.bid WHERE bid.lot_id = ?;";
    @Override
    public Optional<BigDecimal> findLotMinBid(long lotId) throws DAOException {
        Optional<BigDecimal> price = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_MIN_BID)) {
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

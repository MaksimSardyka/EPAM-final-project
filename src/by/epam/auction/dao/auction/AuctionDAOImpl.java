package by.epam.auction.dao.auction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.BaseDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Auction;
import by.epam.auction.domain.AuctionType;

public class AuctionDAOImpl extends BaseDAO<Auction> implements AuctionDAO{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    public AuctionDAOImpl() throws ConnectionPoolException {
        super();
    }

    private static String FIND_ALL = "SELECT id, start_time, finish_time, description, type FROM auction.auction WHERE finish_time > now();";
    @Override
    public Set<Auction> findAll() throws DAOException {
        LOG.log(Level.DEBUG, "findAll auctions");
        Set<Auction> auctionSet = new HashSet<>();
        
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            
            while(resultSet.next()) {
                Auction auction = new Auction();
                auction.setId(resultSet.getLong("id"));
                auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
                auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
                auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);//FIXME - looks strange - ToConst
                auctionSet.add(auction);
                LOG.log(Level.DEBUG, "Auction found: " + auction.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        
        return auctionSet;
    }

    String FIND_AUCTION_BY_ID = "SELECT id, start_time, finish_time, description, type FROM auction.auction WHERE auction.id = ?;";
    
    @Override
    public Optional<Auction> findEntityById(long id) throws DAOException {
        LOG.log(Level.DEBUG, "Request for auction with id = " + id);
        Optional<Auction> optionalAuction;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_AUCTION_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            optionalAuction = Optional.empty();
            if (resultSet.next()) {
                Auction auction = new Auction();
                auction.setId(resultSet.getLong("id"));
                auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
                auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
                auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);//FIXME - looks strange

                optionalAuction = Optional.of(auction);
                LOG.log(Level.DEBUG, "Auction found: " + auction.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return optionalAuction;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Auction entity) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    private static final String CREATE_AUCTION = "INSERT INTO `auction`.`auction` (`start_time`, `finish_time`, `description`, `type`) VALUES (?, ?, ?, ?);";
    @Override
    public boolean create(Auction auction) throws DAOException {
        LOG.log(Level.DEBUG, "Create auction " + auction);
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AUCTION)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(auction.getStartTime()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(auction.getFinishTime()));
            preparedStatement.setString(3, auction.getDescription());
            preparedStatement.setInt(4, auction.getType().ordinal());//0=direct, 1= reverse
            int resultSet = preparedStatement.executeUpdate();

            result = resultSet==1;
            LOG.log(Level.DEBUG, "Auction created");
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return result;
    }

    @Override
    public boolean update(Auction entity) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close(){
    }

    
    
}

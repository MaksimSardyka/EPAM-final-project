package by.epam.auction.dao.lot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import by.epam.auction.domain.Lot;

/**
 * DAO for Lot entity.
 */
public class LotDAOImpl extends BaseDAO<Lot> implements LotDAO {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Find up to specified number of lots.
     */
    private static final String FIND_AUCTION_LOT_LIMIT = "SELECT LOT.id, \r\n" + 
            "        LOT.name, \r\n" + 
            "        LOT.description, \r\n" + 
            "        LOT.is_approved, \r\n" + 
            "        LOT.is_paid, \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        \r\n" + 
            "        CATEGORY.name AS category, \r\n" + 
            "        \r\n" + 
            "        AUCTION.start_time,  \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        AUCTION.id, \r\n" + 
            "        AUCTION.type , \r\n" + 
            "        AUCTION.start_time, \r\n" + 
            "        AUCTION.finish_time, \r\n" + 
            "        AUCTION.id AS auction_id\r\n" + 
            "        \r\n" + 
            "            FROM auction.lot LOT\r\n" + 
            "            JOIN auction.category CATEGORY \r\n" + 
            "                ON LOT.category_id = CATEGORY.id\r\n" + 
            "            JOIN auction.auction AUCTION \r\n" + 
            "                ON LOT.auction_id = AUCTION.id WHERE LOT.is_approved=1 and AUCTION.id = ? LIMIT ?,?"; 
   
    private static final String FIND_LOT_LIMIT = "SELECT LOT.id, \r\n" + 
            "        LOT.name, \r\n" + 
            "        LOT.description, \r\n" + 
            "        LOT.is_approved, \r\n" + 
            "        LOT.is_paid, \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        \r\n" + 
            "        CATEGORY.name AS category, \r\n" + 
            "        \r\n" + 
            "        AUCTION.start_time,  \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        AUCTION.id, \r\n" + 
            "        AUCTION.type , \r\n" + 
            "        AUCTION.start_time, \r\n" + 
            "        AUCTION.finish_time, \r\n" + 
            "        AUCTION.id AS auction_id\r\n" + 
            "        \r\n" + 
            "            FROM auction.lot LOT\r\n" + 
            "            JOIN auction.category CATEGORY \r\n" + 
            "                ON LOT.category_id = CATEGORY.id\r\n" + 
            "            JOIN auction.auction AUCTION \r\n" + 
            "                ON LOT.auction_id = AUCTION.id WHERE LOT.is_approved=1 LIMIT ?,?"; 
    
    private static final String FIND_UNFINISHED_LOT_LIMIT = "SELECT LOT.id, LOT.name, LOT.description, LOT.is_approved, LOT.is_paid, CATEGORY.name AS category, AUCTION.start_time, AUCTION.finish_time, LOT.user_id, LOT.auction_id \r\n" + 
            "FROM auction.lot LOT\r\n" + 
            "    JOIN auction.category CATEGORY ON LOT.category_id = CATEGORY.id \r\n" + 
            "    JOIN auction.auction AUCTION ON LOT.auction_id = AUCTION.id \r\n" + 
            "    WHERE LOT.is_approved=1 LIMIT ?,?";   //FIXME - AND finish_time > now()
    /**
     * Find lot based on it's id.
     */
    private static final String FIND_LOT_ID = "SELECT LOT.id, \r\n" + 
            "        LOT.name, \r\n" + 
            "        LOT.description, \r\n" + 
            "        LOT.is_approved, \r\n" + 
            "        LOT.is_paid, \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        \r\n" + 
            "        CATEGORY.name AS category, \r\n" + 
            "        \r\n" + 
            "        AUCTION.start_time,  \r\n" + 
            "        LOT.user_id, \r\n" + 
            "        AUCTION.id, \r\n" + 
            "        AUCTION.type , \r\n" + 
            "        AUCTION.start_time, \r\n" + 
            "        AUCTION.finish_time, \r\n" + 
            "        AUCTION.id AS auction_id\r\n" + 
            "        \r\n" + 
            "            FROM auction.lot LOT\r\n" + 
            "            JOIN auction.category CATEGORY \r\n" + 
            "                ON LOT.category_id = CATEGORY.id\r\n" + 
            "            JOIN auction.auction AUCTION \r\n" + 
            "                ON LOT.auction_id = AUCTION.id WHERE LOT.id=?"; 

    /**
     * Constructor.
     * @throws ConnectionPoolException
     */
    public LotDAOImpl() throws ConnectionPoolException {
        super();
    }

    /**
     * Find up to specified number of lots.
     * @param number
     * @return
     * @throws DAOException
     */
    public Set<Lot> getLotLimit(int startIndex, int quantity) throws DAOException {
        Set<Lot> lots = new HashSet<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_LOT_LIMIT)) {
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG,
                    "send request: find up to " + quantity + " lot(s) starting from index " + startIndex);

            lots = new HashSet<>();
            while (resultSet.next()) {
                Lot lot = new Lot();
                LOG.log(Level.TRACE, "lot id");
                lot.setId(resultSet.getInt("id"));
                lot.setName(resultSet.getString("name"));
                lot.setDescription(resultSet.getString("description"));
                lot.setApproved(resultSet.getBoolean("is_approved"));
                lot.setPaid(resultSet.getBoolean("is_paid"));
                lot.setCategory(resultSet.getString("category"));
               
                
                LOG.log(Level.TRACE, "auction");
                Auction auction = new Auction();
                LOG.log(Level.TRACE, "start_time");
                auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
                auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, "type");
                auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);
                LOG.log(Level.TRACE, "auction_id");
                auction.setId(resultSet.getLong("auction_id"));
                lot.setAuction(auction);
                
                lots.add(lot);
                LOG.log(Level.DEBUG, "Lot found: " + lot.toString());
            }
        } catch (SQLException e) {
            LOG.log(Level.ERROR, e);
            throw new DAOException(e);
        }
        return lots;
    }

    @Override
    public Set<Lot> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Find lot by its Id.
     * @param id
     * @return
     * @throws DAOException
     */
    @Override
    public Optional<Lot> findEntityById(long id) throws DAOException {
        Optional<Lot> optionalLot;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_LOT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG, "send request: find lot with id = " + id);

            optionalLot = Optional.empty();
            if (resultSet.next()) {
                Lot lot = new Lot();
                lot.setId(resultSet.getLong("id"));
                lot.setName(resultSet.getString("name"));
                lot.setDescription(resultSet.getString("description"));
                lot.setApproved(resultSet.getBoolean("is_approved"));
                lot.setPaid(resultSet.getBoolean("is_paid"));
                lot.setCategory(resultSet.getString("category"));
                
                LOG.log(Level.TRACE, "auction");
                Auction auction = new Auction();
                LOG.log(Level.TRACE, "start_time");
                auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
                auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, "type");
                auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);
                LOG.log(Level.TRACE, "auction_id");
                auction.setId(resultSet.getLong("auction_id"));
                lot.setAuction(auction);

                optionalLot = Optional.of(lot);
                LOG.log(Level.DEBUG, "Lot found: " + lot.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return optionalLot;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Lot entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean create(Lot entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(Lot entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Lot> getUnfinishedLotListLimit(int startIndex, int quantity)
            throws DAOException {
        Set<Lot> lots = new HashSet<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_UNFINISHED_LOT_LIMIT)) {
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG,
                    "send request: find up to " + quantity + " lot(s) starting from index " + startIndex);

            lots = new HashSet<>();
            while (resultSet.next()) {
                Lot lot = new Lot();
                lot.setId(resultSet.getInt("id"));
                lot.setName(resultSet.getString("name"));
                lot.setDescription(resultSet.getString("description"));
                lot.setApproved(resultSet.getBoolean("is_approved"));
                lot.setPaid(resultSet.getBoolean("is_paid"));
                lot.setCategory(resultSet.getString("category"));
                lots.add(lot);
                LOG.log(Level.DEBUG, "Lot found: " + lot.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return lots;
    }
    
    @Override
    public Set<Lot> getAuctionLotLimit(long auctionId, int startIndex, int quantity) throws DAOException {
        LOG.log(Level.DEBUG, "send request: find up to " + quantity + " lot(s) starting from index " + startIndex);
        Set<Lot> lots = new HashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUCTION_LOT_LIMIT)) {
            preparedStatement.setLong(1, auctionId);
            preparedStatement.setInt(2, startIndex);
            preparedStatement.setInt(3, quantity);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                lots = new HashSet<>();
                while (resultSet.next()) {
                        Lot lot = new Lot();
                        lot.setId(resultSet.getLong("id"));
                        LOG.log(Level.TRACE, "id = " + lot.getId());
                        lot.setName(resultSet.getString("name"));
                        LOG.log(Level.TRACE, "name = " + lot.getName());
                        lot.setDescription(resultSet.getString("description"));
                        LOG.log(Level.TRACE, "description = " + lot.getDescription());
                        lot.setApproved(resultSet.getBoolean("is_approved"));
                        LOG.log(Level.TRACE, "is approved = " + lot.isApproved());
                        lot.setPaid(resultSet.getBoolean("is_paid"));
                        LOG.log(Level.TRACE, "is paid = " + lot.isPaid());
                        lot.setCategory(resultSet.getString("category"));
                        LOG.log(Level.TRACE, "category = " + lot.getCategory());
                        
                        LOG.log(Level.TRACE, "auction");
                        Auction auction = new Auction();
                        LOG.log(Level.TRACE, "start_time");
                        auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
                        auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
                        LOG.log(Level.TRACE, "type");
                        auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);
                        LOG.log(Level.TRACE, "auction_id");
                        auction.setId(resultSet.getLong("auction_id"));
                        lot.setAuction(auction);
                        
                        lots.add(lot);
                        LOG.log(Level.DEBUG, "Lot found: " + lot.toString());
                    }
                }
        } catch (SQLException e) {
            LOG.log(Level.ERROR, e);
            throw new DAOException(e);
        }
        return lots;
    }
    
}

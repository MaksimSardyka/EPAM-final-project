package by.epam.auction.dao.lot;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.auction.AuctionDAOImpl;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.AuctionType;
import by.epam.auction.domain.Lot;

/**
 * DAO for Lot entity.
 */
public class LotDAOImpl extends AuctionDAOImpl{
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
   
    private static final String FIND_LOT_LIMIT = "USE auction; Select * FROM( SELECT LOT.id AS lot_id, LOT.name AS lot_name, LOT.description AS lot_description, LOT.is_approved AS lot_is_approved, LOT.is_paid AS lot_is_paid, AUCTION.start_time AS auction_start_time, AUCTION.finish_time AS auction_finish_time, AUCTION.type AS auction_type, LOT.user_id AS owner_id, OWNER.email AS owner_email, OWNER.login AS owner_login, OWNER.is_blocked AS owner_is_blocked, OWNER.frozen_money AS owner_frozen_money, GETPATH(LOT.category_id) AS category_name, CASE WHEN Auction.type = 0 THEN MAX(BID.price) ELSE MIN(BID.price) END AS bid_price, BID.id AS bid_id, BID.user_id AS bidder_id FROM auction.lot LOT JOIN auction.auction AUCTION ON LOT.auction_id = AUCTION.id JOIN auction.user OWNER ON LOT.user_id = OWNER.id JOIN auction.bid BID ON LOT.id = BID.lot_id GROUP BY lot_id LIMIT ?,? ) AS LOTS JOIN auction.image PICTURE ON PICTURE.lot_id = LOTS.lot_id;";
    
    private static final String FIND_UNFINISHED_LOT_LIMIT = "SELECT LOT.id, LOT.name, LOT.description, LOT.is_approved, LOT.is_paid, CATEGORY.name AS category, AUCTION.start_time, AUCTION.finish_time, LOT.user_id, LOT.auction_id \r\n" + 
            "FROM auction.lot LOT\r\n" + 
            "    JOIN auction.category CATEGORY ON LOT.category_id = CATEGORY.id \r\n" + 
            "    JOIN auction.auction AUCTION ON LOT.auction_id = AUCTION.id \r\n" + 
            "    WHERE LOT.is_approved=1 LIMIT ?,?";   //FIXME - AND finish_time > now()
    /**
     * Find lot based on it's id.
     */
    private static final String FIND_LOT_ID = "SELECT \r\n" + 
    		"    LOT.lot_id,\r\n" + 
    		"    lot_name,\r\n" + 
    		"    lot_description,\r\n" + 
    		"    lot_is_approved,\r\n" + 
    		"    lot_is_paid,\r\n" + 
    		"    auction_start_time,\r\n" + 
    		"    auction_finish_time,\r\n" + 
    		"    auction_type,\r\n" + 
    		"    owner_id,\r\n" + 
    		"    owner_email,\r\n" + 
    		"    owner_login,\r\n" + 
    		"    owner_is_blocked,\r\n" + 
    		"    owner_frozen_money,\r\n" + 
    		"    category_name,\r\n" + 
    		"    bid_price,\r\n" + 
    		"    bid_id,\r\n" + 
    		"    bidder_id,\r\n" + 
    		"    picture_name\r\n" + 
    		"FROM\r\n" + 
    		"    (SELECT \r\n" + 
    		"        LOT.id AS lot_id,\r\n" + 
    		"            LOT.name AS lot_name,\r\n" + 
    		"            LOT.description AS lot_description,\r\n" + 
    		"            LOT.is_approved AS lot_is_approved,\r\n" + 
    		"            LOT.is_paid AS lot_is_paid,\r\n" + 
    		"            AUCTION.start_time AS auction_start_time,\r\n" + 
    		"            AUCTION.finish_time AS auction_finish_time,\r\n" + 
    		"            AUCTION.type AS auction_type,\r\n" + 
    		"            LOT.user_id AS owner_id,\r\n" + 
    		"            OWNER.email AS owner_email,\r\n" + 
    		"            OWNER.login AS owner_login,\r\n" + 
    		"            OWNER.is_blocked AS owner_is_blocked,\r\n" + 
    		"            OWNER.frozen_money AS owner_frozen_money,\r\n" + 
    		"            GETPATH(LOT.category_id) AS category_name,\r\n" + 
    		"            BID.price AS bid_price,\r\n" + 
    		"            BID.id AS bid_id,\r\n" + 
    		"            BID.user_id AS bidder_id\r\n" + 
    		"    FROM\r\n" + 
    		"        auction.lot LOT\r\n" + 
    		"    JOIN auction.auction AUCTION ON LOT.auction_id = AUCTION.id\r\n" + 
    		"    JOIN auction.user OWNER ON LOT.user_id = OWNER.id\r\n" + 
    		"    JOIN auction.bid BID ON LOT.id = BID.lot_id\r\n" + 
    		"    WHERE\r\n" + 
    		"        LOT.id = ?\r\n" + 
    		"    ORDER BY (CASE\r\n" + 
    		"        WHEN auction_type = 0 THEN bid_price\r\n" + 
    		"    END) DESC , (CASE\r\n" + 
    		"        WHEN auction_type = 1 THEN bid_price\r\n" + 
    		"    END) ASC\r\n" + 
    		"    LIMIT 1) AS LOT\r\n" + 
    		"        JOIN\r\n" + 
    		"    auction.image PICTURE ON LOT.lot_id = PICTURE.lot_id;";
     /**
     * Constructor.
     * @throws ConnectionPoolException
     */
    public LotDAOImpl() throws ConnectionPoolException {
        super();
    }

    private static final String SQL_CATEGORY = "SELECT id, getpath(id) AS path FROM category WHERE id NOT IN (SELECT parent_id FROM category WHERE parent_id IS NOT NULL)";
    public Map<Long, String> takeCategoryPathMap() throws SQLException{
    	Map<Long, String> categoryMap = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
        	ResultSet rs = statement.executeQuery(SQL_CATEGORY);
        	while(rs.next()) {
        		categoryMap.put(rs.getLong("id"), rs.getString("path"));
        	}
        }
    	return categoryMap;
    }
    
    /*
     * Find up to specified number of lots.
     * @param number
     * @return
     * @throws DAOException
     */
    public Set<Lot> takeLotLimit(int startIndex, int quantity) throws DAOException {
        Set<Lot> lots = new HashSet<>();
        Long previousId = -1L;
        Lot lot = null;
        Set<String> pictures = new HashSet<>();
        
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_LOT_LIMIT)) {
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG,"Requested up to " + quantity + " lot(s) starting from index " + startIndex);
            if (resultSet.next()) {
            boolean isNextExists = true;
            while(isNextExists){
            	lot = new Lot();
                lot.setId(resultSet.getLong("lot_id"));
                LOG.log(Level.TRACE, lot.getId());
                lot.setName(resultSet.getString("lot_name"));
                LOG.log(Level.TRACE, lot.getName());
                lot.setDescription(resultSet.getString("lot_description"));
                LOG.log(Level.TRACE, lot.getDescription());
                lot.setApproved(resultSet.getBoolean("lot_is_approved"));
                LOG.log(Level.TRACE, lot.isApproved());
                lot.setPaid(resultSet.getBoolean("lot_is_paid"));
                LOG.log(Level.TRACE, lot.isPaid());
                lot.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("auction_start_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, lot.getStartTime());
                lot.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("auction_finish_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, lot.getFinishTime());
                lot.setAuctionType(AuctionType.values()[resultSet.getInt("auction_type")]);
                LOG.log(Level.TRACE, lot.getAuctionType());
                lot.setOwnerId(resultSet.getLong("owner_id"));
                LOG.log(Level.TRACE, lot.getOwnerId());
                lot.setOwnerEmail(resultSet.getString("owner_email"));
                LOG.log(Level.TRACE, lot.getOwnerEmail());
                lot.setOwnerLogin(resultSet.getString("owner_login"));
                LOG.log(Level.TRACE, lot.getOwnerLogin());
                lot.setOwnerIsBlocked(resultSet.getBoolean("owner_is_blocked"));
                LOG.log(Level.TRACE, lot.isOwnerIsBlocked());
                lot.setOwnerFrozenMoney(resultSet.getBigDecimal("owner_frozen_money"));
                LOG.log(Level.TRACE, lot.getOwnerFrozenMoney());
                lot.setCategory(resultSet.getString("category_name"));
                LOG.log(Level.TRACE, lot.getCategory());
                lot.setBidPrice(resultSet.getBigDecimal("bid_price"));
                LOG.log(Level.TRACE, lot.getBidPrice());
                lot.setBidId(resultSet.getLong("bid_id"));
                LOG.log(Level.TRACE, lot.getBidId());
                lot.setBidderId(resultSet.getLong("bidder_id"));
                LOG.log(Level.TRACE, lot.getBidderId());
                pictures = new HashSet<>();
            	do {
            		LOG.log(Level.TRACE, "Parse lot picture");
            		previousId = resultSet.getLong("lot_id");
            		String pictureName = resultSet.getString("picture_name");
            		LOG.log(Level.TRACE, pictureName);
            		pictures.add(pictureName);
            		isNextExists = resultSet.next();
            	} while(isNextExists == true && resultSet.getLong("lot_id") == previousId);
            	lot.setPictureSet(pictures);
            	lots.add(lot);
            }
        }
        } catch (SQLException e) {
            LOG.log(Level.ERROR, e);
            throw new DAOException(e);
        }
        return lots;
    }

    /**
     * Find lot by its Id.
     * @param id
     * @return
     * @throws DAOException
     */
    public Optional<Lot> findLotById(long id) throws DAOException {
    	LOG.log(Level.DEBUG, "Request lot with id = " + id);
        Optional<Lot> lotOptional = Optional.empty();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_LOT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG, "send request: find lot with id = " + id);
            Lot lot = null;
            Set<String> pictures = new HashSet<>();
            if (resultSet.next()) {
            	lot = new Lot();
                lot.setId(resultSet.getLong("lot_id"));
                LOG.log(Level.TRACE, lot.getId());
                lot.setName(resultSet.getString("lot_name"));
                LOG.log(Level.TRACE, lot.getName());
                lot.setDescription(resultSet.getString("lot_description"));
                LOG.log(Level.TRACE, lot.getDescription());
                lot.setApproved(resultSet.getBoolean("lot_is_approved"));
                LOG.log(Level.TRACE, lot.isApproved());
                lot.setPaid(resultSet.getBoolean("lot_is_paid"));
                LOG.log(Level.TRACE, lot.isPaid());
                lot.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("auction_start_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, lot.getStartTime());
                lot.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("auction_finish_time").toInstant(), ZoneId.systemDefault()));
                LOG.log(Level.TRACE, lot.getFinishTime());
                lot.setAuctionType(AuctionType.values()[resultSet.getInt("auction_type")]);
                LOG.log(Level.TRACE, lot.getAuctionType());
                lot.setOwnerId(resultSet.getLong("owner_id"));
                LOG.log(Level.TRACE, lot.getOwnerId());
                lot.setOwnerEmail(resultSet.getString("owner_email"));
                LOG.log(Level.TRACE, lot.getOwnerEmail());
                lot.setOwnerLogin(resultSet.getString("owner_login"));
                LOG.log(Level.TRACE, lot.getOwnerLogin());
                lot.setOwnerIsBlocked(resultSet.getBoolean("owner_is_blocked"));
                LOG.log(Level.TRACE, lot.isOwnerIsBlocked());
                lot.setOwnerFrozenMoney(resultSet.getBigDecimal("owner_frozen_money"));
                LOG.log(Level.TRACE, lot.getOwnerFrozenMoney());
                lot.setCategory(resultSet.getString("category_name"));
                LOG.log(Level.TRACE, lot.getCategory());
                lot.setBidPrice(resultSet.getBigDecimal("bid_price"));
                LOG.log(Level.TRACE, lot.getBidPrice());
                lot.setBidId(resultSet.getLong("bid_id"));
                LOG.log(Level.TRACE, lot.getBidId());
                lot.setBidderId(resultSet.getLong("bidder_id"));
                LOG.log(Level.TRACE, lot.getBidderId());               
                String pictureName = resultSet.getString("picture_name");
                LOG.log(Level.TRACE, pictureName);
                pictures.add(pictureName);
            }
            while(resultSet.next()) {
                String pictureName = resultSet.getString("picture_name");
                LOG.log(Level.TRACE, pictureName);
                pictures.add(pictureName);
            }
            if(lot != null) {
            	LOG.log(Level.TRACE, "Parsed " + pictures.size() + " picture(s)" );
            	lot.setPictureSet(pictures);
            }
            lotOptional = Optional.ofNullable(lot);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return lotOptional;
    }

    
    public boolean deleteLot(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    
    public boolean deleteLot(Lot entity) {
        // TODO Auto-generated method stub
        return false;
    }

   
    public boolean updateLot(Lot entity) {
        // TODO Auto-generated method stub
        return false;
    }

    
    public Set<Lot> takeUnfinishedLotListLimit(int startIndex, int quantity)
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
                lot.setId(resultSet.getLong("id"));
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
    
    
    public Set<Lot> takeAuctionLotLimit(long auctionId, long l, int quantity) throws DAOException {
        LOG.log(Level.DEBUG, "send request: find up to " + quantity + " lot(s) starting from index " + l);
        Set<Lot> lots = new HashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUCTION_LOT_LIMIT)) {
            preparedStatement.setLong(1, auctionId);
            preparedStatement.setLong(2, l);
            preparedStatement.setInt(3, quantity);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
                        
//                        LOG.log(Level.TRACE, "auction");
//                        Auction auction = new Auction();
//                        LOG.log(Level.TRACE, "start_time");
//                        auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(), ZoneId.systemDefault()));
//                        auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(), ZoneId.systemDefault()));
//                        LOG.log(Level.TRACE, "type");
//                        auction.setType(resultSet.getInt("type")==0?AuctionType.DIRECT:AuctionType.REVERSE);
//                        LOG.log(Level.TRACE, "auction_id");
//                        auction.setId(resultSet.getLong("auction_id"));
                        
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

    private static final String SQL_PROPOSED_LOT_SET = "SELECT id, name, description, is_approved, is_paid, category_id, user_id, auction_id \r\n" + 
    		"	FROM auction.lot\r\n" + 
    		"    WHERE lot.is_approved = 0;";
	public Set<Lot> takeProposedLotSet() throws SQLException {
        LOG.log(Level.DEBUG, "Send request for proposed lot set.");
        Set<Lot> lotSet = new HashSet<>();
		try(Statement statement = connection.createStatement()){
			ResultSet resultSet = statement.executeQuery(SQL_PROPOSED_LOT_SET);
                while (resultSet.next()) {
                    Lot proposedLot = new Lot();
                    proposedLot.setId(resultSet.getLong("id"));
                    LOG.log(Level.TRACE, "id = " + proposedLot.getId());
                    proposedLot.setName(resultSet.getString("name"));
                    LOG.log(Level.TRACE, "name = " + proposedLot.getName());
                    proposedLot.setDescription(resultSet.getString("description"));
                    LOG.log(Level.TRACE, "description = " + proposedLot.getDescription());
                    proposedLot.setApproved(resultSet.getBoolean("is_approved"));
                    LOG.log(Level.TRACE, "is approved = " + proposedLot.isApproved());
                    proposedLot.setPaid(resultSet.getBoolean("is_paid"));
                    LOG.log(Level.TRACE, "is paid = " + proposedLot.isPaid());
                    //add category retrieval
                    lotSet.add(proposedLot);
                }
		}
		return lotSet;
	}

	public Long proposeLot(Long userId, Long auctionId, String lotName, String lotDescription, BigDecimal amount,
			Long categoryId, String fileName1, String fileName2, String fileName3) {
		// TODO Auto-generated method stub
		return null;
	}
    
}

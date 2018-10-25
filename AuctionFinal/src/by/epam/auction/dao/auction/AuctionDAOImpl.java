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

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.user.UserDAOImpl;
import by.epam.auction.domain.Auction;
import by.epam.auction.domain.AuctionType;

/**
 * The Class AuctionDAOImpl.
 */
public class AuctionDAOImpl extends UserDAOImpl {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/** The Constant FIND_ALL. */
	private static final String FIND_ALL = "SELECT id, start_time, finish_time, description, type FROM auction.auction WHERE finish_time > now();";
	
	/** The Constant FIND_AUCTION_BY_ID. */
	private static final String FIND_AUCTION_BY_ID = "SELECT id, start_time, finish_time, description, type FROM auction.auction WHERE auction.id = ?;";
	
	/** The Constant CREATE_AUCTION. */
	private static final String CREATE_AUCTION = "INSERT INTO `auction`.`auction` (`start_time`, `finish_time`, `description`, `type`) VALUES (?, ?, ?, ?);";
	
	/** The Constant SQL_COUNT. */
	private static final String SQL_COUNT = "SELECT COUNT(id) FROM `auction`.`auction` WHERE finish_time > now();";

	/**
	 * Instantiates a new auction DAO impl.
	 *
	 * @throws ConnectionPoolException
	 *             the connection pool exception
	 */
	public AuctionDAOImpl() throws ConnectionPoolException {
		super();
	}

	/**
	 * Find all auction.
	 *
	 * @return the sets the
	 * @throws DAOException
	 *             the DAO exception
	 */
	public Set<Auction> findAllAuction() throws DAOException {
		LOG.log(Level.DEBUG, "findAll auctions");
		Set<Auction> auctionSet = new HashSet<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Auction auction = new Auction();
				auction.setId(resultSet.getLong("id"));
				auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(),
						ZoneId.systemDefault()));
				auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(),
						ZoneId.systemDefault()));
				auction.setType(AuctionType.values()[resultSet.getInt("type")]);
				auctionSet.add(auction);
				LOG.log(Level.DEBUG, "Auction found: " + auction.toString());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return auctionSet;
	}

	/**
	 * Find auction by id.
	 *
	 * @param id
	 *            the id
	 * @return the optional
	 * @throws DAOException
	 *             the DAO exception
	 */
	public Optional<Auction> findAuctionById(long id) throws DAOException {
		LOG.log(Level.DEBUG, "Request for auction with id = " + id);
		Optional<Auction> optionalAuction;
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUCTION_BY_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			optionalAuction = Optional.empty();
			if (resultSet.next()) {
				Auction auction = new Auction();
				auction.setId(resultSet.getLong("id"));
				auction.setStartTime(LocalDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(),
						ZoneId.systemDefault()));
				auction.setFinishTime(LocalDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(),
						ZoneId.systemDefault()));
				auction.setType(AuctionType.values()[resultSet.getInt("type")]);
				auction.setDescription(resultSet.getString("description"));

				optionalAuction = Optional.of(auction);
				LOG.log(Level.DEBUG, "Auction found: " + auction.toString());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return optionalAuction;
	}

	/**
	 * Creates the auction.
	 *
	 * @param startTime
	 *            the start time
	 * @param finishTime
	 *            the finish time
	 * @param description
	 *            the description
	 * @param type
	 *            the type
	 * @return true, if successful
	 * @throws DAOException
	 *             the DAO exception
	 */
	public boolean createAuction(LocalDateTime startTime, LocalDateTime finishTime, String description,
			AuctionType type) throws DAOException {
		LOG.log(Level.DEBUG, "Create auction.");
		boolean result = false;
		try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AUCTION)) {
			preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
			preparedStatement.setTimestamp(2, Timestamp.valueOf(finishTime));
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, type.ordinal());
			int resultSet = preparedStatement.executeUpdate();

			result = resultSet == 1;
			LOG.log(Level.DEBUG, "Auction " + (result ? "" : "not " + "created"));
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result;
	}

	/**
	 * Gets the count auction.
	 *
	 * @return the count auction
	 * @throws DAOException
	 *             the DAO exception
	 */
	public Long getCountAuction() throws DAOException {
		LOG.log(Level.DEBUG, "Request for active auctions count");
		Optional<Long> optionalCount = Optional.empty();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Long count = resultSet.getLong(1);
				optionalCount = Optional.of(count);
				LOG.log(Level.DEBUG, "Auctions count: " + count);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return optionalCount.orElseThrow(() -> new DAOException("Null auction count"));
	}

}

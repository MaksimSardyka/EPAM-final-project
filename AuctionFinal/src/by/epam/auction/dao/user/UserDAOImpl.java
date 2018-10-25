package by.epam.auction.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.BaseDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;

/**
 * DAO for User Entity.
 */
public class UserDAOImpl extends BaseDAO implements AutoCloseable{
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Constructor.
	 * 
	 * @throws ConnectionPoolException
	 */
	public UserDAOImpl() throws ConnectionPoolException {
		super();
	}

	/**
	 * Create user based on provided credentials.
	 */
	private static final String CREATE_USER = "INSERT INTO `auction`.`user` (`email`, `login`, `password`, `role`, `money`, `is_blocked`, `frozen_money`) VALUES (?, ?, MD5(?), '1', '0.00', '0', '0.00');";

	private static final String FIND_USER_ID_USERNAME_PASSWORD = "SELECT `id`, `role`, `email`, `login`, `money`, `frozen_money`, `is_blocked` FROM `auction`.`user` WHERE `login` LIKE ? AND `password` LIKE MD5(?);";

	private static final String FIND_USER_BY_ID = "SELECT `id`, `role`, `email`, `login`, `money`, `frozen_money`, `is_blocked` FROM `auction`.`user` WHERE `id` = ?;";

	private static final String FIND_USER_LIMIT = "SELECT `id`, `role`, `email`, `login`, `money`, `frozen_money`, `is_blocked` FROM `auction`.`user`LIMIT ?,?;";

	private static final String UPDATE_USER = "UPDATE `auction`.`user` SET `role`=?, `email`=?, `login`=?, `money`=?, `frozen_money`=?, `is_blocked`=? WHERE `id`=?;";

	private static final String UPDATE_PASSWORD = "UPDATE `auction`.`user` SET `password` = MD5(?) WHERE `id`=?";

	/**
	 * Find user by login and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	public Optional<User> findUserByUsernamePassword(String username, String password) throws DAOException {
		LOG.log(Level.DEBUG, "User DAO looking for user:" + username);
		Optional<User> optionalUser = Optional.empty();
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ID_USERNAME_PASSWORD)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			LOG.log(Level.TRACE, "Request was sent.");

			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				LOG.log(Level.TRACE, "user Id = " + user.getId());
				// 0 = ADMINISTRATOR
				user.setRole(Role.getById(resultSet.getInt("role")));
				LOG.log(Level.TRACE, "user Role = " + user.getRole());
				user.setEmail(resultSet.getString("email"));
				LOG.log(Level.TRACE, "user Email = " + user.getEmail());
				user.setLogin(resultSet.getString("login"));
				LOG.log(Level.TRACE, "user Login = " + user.getLogin());
				user.setMoney(resultSet.getBigDecimal("money"));
				LOG.log(Level.TRACE, "user Money = " + user.getMoney());
				user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
				LOG.log(Level.TRACE, "user FrozenMoney = " + user.getFrozenMoney());
				user.setBlocked(resultSet.getBoolean("is_blocked"));// 1 = blocked
				LOG.log(Level.TRACE, "user isBlocked = " + user.isBlocked());
				optionalUser = Optional.of(user);
			} else {
				LOG.log(Level.DEBUG, "User matching credentials wasn\'t found.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return optionalUser;
	}

	public boolean updatePassword(long id, String password) throws DAOException {
		LOG.log(Level.DEBUG, "Upodate password for user with id " + id);
		int rowChangeNumber = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
			preparedStatement.setString(1, password);
			preparedStatement.setLong(2, id);

			rowChangeNumber = preparedStatement.executeUpdate();
			if (rowChangeNumber == 1) {
				LOG.log(Level.DEBUG, "Password changed for user with id: " + id);
			} else {
				LOG.log(Level.ERROR, "Unable to update passworrd for user with id:" + id);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return rowChangeNumber == 1;
	}

	public Set<User> findUserLimit(long startIndex, int userPerPage) throws DAOException {
		LOG.log(Level.DEBUG, "Looking for all users");
		Set<User> users = new HashSet<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_LIMIT)) {
			preparedStatement.setLong(1, startIndex);
			preparedStatement.setInt(2, userPerPage);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				// 0 = ADMINISTRATOR
				user.setRole(Role.getById(resultSet.getInt("role")));
				user.setEmail(resultSet.getString("email"));
				user.setLogin(resultSet.getString("login"));
				user.setMoney(resultSet.getBigDecimal("money"));
				user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
				// 1 = blocked
				user.setBlocked(resultSet.getBoolean("is_blocked"));

				users.add(user);
				LOG.log(Level.DEBUG, "User found: " + user.toString());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return users;
	}

	public Optional<User> findUserById(long id) throws DAOException {
		LOG.log(Level.DEBUG, "DAO - find user by id:" + id);
		Optional<User> optionalUser = Optional.empty();
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LOG.log(Level.DEBUG, "User by id:" + id + " found");
				User user = new User();
				user.setId(resultSet.getLong("id"));
				// 0 = ADMINISTRATOR
				user.setRole(Role.getById(resultSet.getInt("role")));
				user.setEmail(resultSet.getString("email"));
				user.setLogin(resultSet.getString("login"));
				user.setMoney(resultSet.getBigDecimal("money"));
				user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
				// 1 = blocked
				user.setBlocked(resultSet.getBoolean("is_blocked"));

				optionalUser = Optional.of(user);
			} else {
				LOG.log(Level.DEBUG, "User by id:" + id + " not found");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return optionalUser;
	}

	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteUser(User entity) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean createUser(String email, String username, String password) throws DAOException {
		LOG.log(Level.DEBUG, "createUser: start");
		int rowCount = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, password);
			rowCount = preparedStatement.executeUpdate();
			LOG.log(Level.DEBUG, "send request: create user");
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		}
		return rowCount == 1;
	}

	public boolean updateUser(User user) throws DAOException {
		LOG.log(Level.DEBUG, "Update user:" + user);
		int rowChangeNumber = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
			// 0 = ADMINISTRATOR
			preparedStatement.setInt(1, user.getRole().ordinal());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setBigDecimal(4, user.getMoney());
			preparedStatement.setBigDecimal(5, user.getFrozenMoney());
			// 1 = blocked
			preparedStatement.setInt(6, user.isBlocked() ? 1 : 0);
			preparedStatement.setLong(7, user.getId());
			rowChangeNumber = preparedStatement.executeUpdate();
			LOG.log(Level.DEBUG, "User by id:" + user.getId() + (rowChangeNumber == 1 ? " " : " not") + " updated");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return rowChangeNumber == 1;
	}

}

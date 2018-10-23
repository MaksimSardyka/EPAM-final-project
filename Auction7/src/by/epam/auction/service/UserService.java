package by.epam.auction.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.user.UserDAOImpl;
import by.epam.auction.domain.User;
import by.epam.auction.service.exception.ServiceException;

public class UserService {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Find user with matching login and password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public Optional<User> findUserByLoginPassword(String username, String password) throws ServiceException {
		LOG.log(Level.DEBUG, "Service looking for user by username and password");

		Optional<User> user = Optional.empty();
		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			user = userDAO.findUserByUsernamePassword(username, password);
			LOG.log(Level.DEBUG, "User " + (user.isPresent()?user.get():"not found"));
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return user;
	}

	public Optional<User> findUserById(Long userId) throws ServiceException {
		LOG.log(Level.DEBUG, "Service looking for user by user id");

		Optional<User> user = Optional.empty();
		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			user = userDAO.findUserById(userId);
			LOG.log(Level.DEBUG, "DAO " + (user.isPresent() ? "found" : "not found") + " user");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return user;
	}

	/**
	 * Create user based on provided parameters.
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public User createUser(String email, String username, String password) throws ServiceException {
		User user = null;

		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			if (userDAO.createUser(email, username, password)) {
				user = userDAO.findUserByUsernamePassword(username, password).orElse(null);
			}
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}

		LOG.log(Level.DEBUG, user.toString() + " had " + (null == user ? "not" : "") + "been created.");
		return user;
	}

	public User updateUserInfo(Long userId, String email, String password) throws ServiceException {
		User user = null;

		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			Optional<User> optionalUser = userDAO.findUserById(userId);
			if (optionalUser.isPresent()) {
				user = optionalUser.get();
				user.setEmail(email);
				if (userDAO.updateUser(user)) {
					if (userDAO.updatePassword(userId, password)) {
						optionalUser = userDAO.findUserById(userId);
						if (optionalUser.isPresent()) {
							user = optionalUser.get();
							LOG.log(Level.DEBUG, "user: " + user.getLogin() + " had been updated.");
						} else {
							userDAO.rollbackTransaction();
							LOG.log(Level.DEBUG, "user: " + user.getLogin() + " had not been updated.");
						}
					} else {
						userDAO.rollbackTransaction();
					}
				} else {
					userDAO.rollbackTransaction();
				}
			}
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	public Optional<Set<User>> takeUserSet(long startIndex, int userPerPage) throws ServiceException {
		LOG.log(Level.DEBUG, "Service looking for user by username and password");

		Optional<Set<User>> userSet = Optional.empty();
		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userSet = Optional.of(userDAO.findUserLimit(startIndex, userPerPage));
			LOG.log(Level.DEBUG, "User set was " + (userSet.isPresent() ? "" : "not") + " found user");
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return userSet;
	}

	public void blockUser(Long userId) throws ServiceException {
		boolean isSuccess = false;
		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			Optional<User> optionalUser = userDAO.findUserById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setBlocked(true);
				isSuccess = userDAO.updateUser(user);
			}
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		LOG.log(Level.DEBUG, "user with id: " + userId + " had " + (isSuccess ? "" : "not") + " been blocked.");
	}

	public void unblockUser(Long userId) throws ServiceException {
		boolean isSuccess = false;
		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			Optional<User> optionalUser = userDAO.findUserById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setBlocked(false);
				isSuccess = userDAO.updateUser(user);
			}
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		LOG.log(Level.DEBUG, "user with id: " + userId + " had " + (isSuccess ? "" : "not") + " been unblocked.");
	}

	public User addFunds(Long userId, BigDecimal amount) throws ServiceException {
		User user = null;
		boolean isOk = false;

		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			Optional<User> optionalUser = userDAO.findUserById(userId);
			if (optionalUser.isPresent()) {
				User newUser = optionalUser.get();
				newUser.setMoney(newUser.getMoney().add(amount));
				if (userDAO.updateUser(newUser)) {
					user = newUser;
					LOG.log(Level.DEBUG, "user with id:" + userId + " had been updated.");
				} else {
					userDAO.rollbackTransaction();
				}
			} 
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		if (!isOk) {
			LOG.log(Level.DEBUG, "user with id:" + userId + " had not been found.");
		}
		return user;
	}

	public User withdrowFunds(Long userId, BigDecimal amount) throws ServiceException {
		User user = null;
		boolean isOk = false;

		try (UserDAOImpl userDAO = new UserDAOImpl()) {
			userDAO.startTransaction();
			Optional<User> optionalUser = userDAO.findUserById(userId);
			if (optionalUser.isPresent() && !optionalUser.get().isBlocked()) {
				User newUser = optionalUser.get();
				if (newUser.getMoney().compareTo(amount) >= 0 && !newUser.isBlocked()) {
					newUser.setMoney(newUser.getMoney().add(amount.negate()));
					if (userDAO.updateUser(newUser)) {
						user = newUser;
						LOG.log(Level.DEBUG, "user with id:" + userId + " had been updated.");
						isOk = true;
					} else {
						userDAO.rollbackTransaction();
					}
				}
			}
			userDAO.finishTransaction();
		} catch (ConnectionPoolException | DAOException e) {
			throw new ServiceException(e);
		}
		if (!isOk) {
			LOG.log(Level.DEBUG, "user with id:" + userId + " had not been found.");
		}
		return user;
	}
}

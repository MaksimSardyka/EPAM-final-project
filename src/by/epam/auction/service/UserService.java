package by.epam.auction.service;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.user.UserDAOImpl;
import by.epam.auction.domain.Role;
import by.epam.auction.domain.User;
import by.epam.auction.service.exception.ServiceException;

public class UserService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Find user with matching login and password.
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    public Optional<User> findUserByLoginPassword(String username,
            String password) throws ServiceException {
        LOG.log(Level.DEBUG,
                "Service looking for user by username and password");

        Optional<User> user = Optional.empty();
        try (UserDAOImpl userDAO = new UserDAOImpl()) {
            user = userDAO.findUserByUsernamePassword(username, password);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Create user based on provided parameters.
     * @param email
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    public boolean createUser(String email, String username, String password)
            throws ServiceException {
        boolean result = false;
        User user = new User();
        user.setEmail(email);
        user.setLogin(username);
        user.setRole(Role.USER);

        try (UserDAOImpl userDAO = new UserDAOImpl()) {
            result = userDAO.create(user, password);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }

        LOG.log(Level.DEBUG, user.toString() + " had been created.");
        return result;
    }
}

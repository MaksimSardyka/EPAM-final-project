package by.epam.auction.dao.user;

import java.util.Optional;

import by.epam.auction.dao.EntityDAO;
import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.domain.User;

public interface UserDAO extends EntityDAO<User> {
    Optional<User> findUserByUsernamePassword(String username,
            String password) throws DAOException;

    boolean updatePassword(int id, String password)
            throws DAOException;

}

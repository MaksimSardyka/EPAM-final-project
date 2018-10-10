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
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
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

    private static final String FIND_USER_USERNAME_PASSWORD = "SELECT id, email, login, password, role, money, is_blocked, frozen_money FROM auction.user WHERE (login = ?) AND (password = MD5(?));";

    private static final String FIND_USER_BY_ID = "SELECT id, role, email, login, money, frozen_money, is_blocked FROM auction.user WHERE id = ?;";

    private static final String FIND_ALL = "SELECT id, role, email, login, money, frozen_money, is_blocked FROM auction.user;";

    private static final String UPDATE_USER = "UPDATE `auction`.`user` SET `role`='?', `email`='?', `login`='?', `money`='?', `frozen_money`='?', `is_blocked`='?' WHERE `id`='?';";
    
    private static final String UPDATE_PASSWORD = "UPDATE `auction`.`user` SET `password` = MD5(?) WHERE `id`=?";
    /**
     * Find user by login and password.
     * 
     * @param username
     * @param password
     * @return
     * @throws DAOException
     */
    public Optional<User> findUserByUsernamePassword(String username,
            String password) throws DAOException {
        LOG.log(Level.DEBUG,
                "User DAO looking for user matching pr");
        Optional<User> optionalUser;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_USER_USERNAME_PASSWORD)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOG.log(Level.DEBUG,
                    "send request: find user by username, password");

            optionalUser = Optional.empty();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                // 0 = ADMINISTRATOR
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setMoney(resultSet.getBigDecimal("money"));
                user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
                // 1 = blocked
                user.setBlocked(resultSet.getBoolean("is_blocked"));
                optionalUser = Optional.of(user);
                LOG.log(Level.DEBUG, "User found: " + user.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);

        }
        return optionalUser;
    }
    
    //TODO - for future change password operation
    public boolean updatePassword(int id, String password) throws DAOException{
        LOG.log(Level.DEBUG,"Upodate password for user with id " + id);
        int rowChangeNumber = 0;
        
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(UPDATE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            
            rowChangeNumber = preparedStatement.executeUpdate();
            if (rowChangeNumber == 1) {
                LOG.log(Level.DEBUG, "Password chnged for user with id: " + id);
            } else {
                LOG.log(Level.ERROR, "Unable to update passworrd for user with id:" + id);//TODO ERROR or DEBUG??? 
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowChangeNumber == 1;
    }

    @Override
    public Set<User> findAll() throws DAOException {
        LOG.log(Level.DEBUG, "Looking for all users");
        Set<User> users = new HashSet<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                // 0 = ADMINISTRATOR
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setMoney(resultSet.getBigDecimal("money"));
                user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
                // 1 = blocked
                user.setBlocked(1 == resultSet.getInt("is_blocked"));

                users.add(user);
                LOG.log(Level.DEBUG, "User found: " + user.toString());
            }
            connection.closeStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public Optional<User> findEntityById(long id) throws DAOException {
        LOG.log(Level.DEBUG, "DAO - find user by id:" + id);
        Optional<User> optionalUser = Optional.empty();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOG.log(Level.DEBUG, "User by id:" + id + " found");
                User user = new User();
                user.setId(resultSet.getInt("id"));
                // 0 = ADMINISTRATOR
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setEmail(resultSet.getString("email"));
                user.setLogin(resultSet.getString("login"));
                user.setMoney(resultSet.getBigDecimal("money"));
                user.setFrozenMoney(resultSet.getBigDecimal("frozen_money"));
                // 1 = blocked
                user.setBlocked(1 == resultSet.getInt("is_blocked"));

                optionalUser = Optional.of(user);
            } else {
                LOG.log(Level.DEBUG, "User by id:" + id + " not found");
            }
            connection.closeStatement(preparedStatement);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(User entity) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Create user.
     * 
     * @param user
     * @return
     * @throws DAOException
     */
    public boolean create(User user, String password) throws DAOException {
        int resultSet;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, password);
            resultSet = preparedStatement.executeUpdate();
            LOG.log(Level.DEBUG, "send request: create user");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return resultSet == 1;
    }

    @Override
    public boolean update(User user) throws DAOException {
        LOG.log(Level.DEBUG, "Update user:" + user);
        int rowChangeNumber = 0;
        
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(UPDATE_USER)) {
            // 0 = ADMINISTRATOR
            preparedStatement.setInt(1, user.getRole().ordinal());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setBigDecimal(4, user.getMoney());
            preparedStatement.setBigDecimal(5, user.getFrozenMoney());
            // 1 = blocked
            preparedStatement.setInt(6, user.isBlocked()?1:0);
            preparedStatement.setLong(7, user.getId());
            
            rowChangeNumber = preparedStatement.executeUpdate();
            if (rowChangeNumber == 1) {
                LOG.log(Level.DEBUG, "User by id:" + user.getId() + " updated");
            } else {
                LOG.log(Level.ERROR, "User by id:" + user.getId() + " not updated");//TODO ERROR or DEBUG??? 
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return rowChangeNumber == 1;
    }

    @Override
    public boolean create(User entity) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }
}

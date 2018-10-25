package by.epam.auction.dao;

import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.dao.exception.DAOException;
import by.epam.auction.dao.pool.ConnectionPool;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.pool.ProxyConnection;

public class BaseDAO implements AutoCloseable{
    /**
     * Logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();   
    /**
     * {@link ProxyConnection} connection. 
     */
    protected ProxyConnection connection;

    public BaseDAO() throws ConnectionPoolException {
        connection = ConnectionPool.getInstance().takeConnection();
    }
    
    public void startTransaction() throws DAOException {
    	try {
			connection.setAutoCommit(false);
			LOG.log(Level.DEBUG, "start Transaction");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
    }
    
    public void finishTransaction() throws DAOException {
    	try {
			connection.setAutoCommit(true);
			LOG.log(Level.DEBUG, "finish Transaction");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
    }
	
    public void rollbackTransaction() throws DAOException {
    	try {
			connection.rollback();
			LOG.log(Level.DEBUG, "rollback Transaction");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
    }

    /**
     * Returns this connection to the connection pool.
     * @throws DAOException 
     */
    @Override
    public void close() throws DAOException {
        LOG.log(Level.DEBUG, "Proxy connection closed");
        connection.close();
    }
}
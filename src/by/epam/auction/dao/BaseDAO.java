package by.epam.auction.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.epam.auction.dao.pool.ConnectionPool;
import by.epam.auction.dao.pool.ConnectionPoolException;
import by.epam.auction.dao.pool.ProxyConnection;
import by.epam.auction.domain.Entity;

public abstract class BaseDAO<T extends Entity> implements AutoCloseable{
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
    
    @Override
    public void close() {
        LOG.log(Level.DEBUG, "Connection closed");
        connection.close();
    }

    void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }
}
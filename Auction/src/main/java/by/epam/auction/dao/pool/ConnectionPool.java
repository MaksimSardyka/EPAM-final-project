package by.epam.auction.dao.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.auction.resource.DBResourceManager;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Class {@code ConnectionPool} contains the specified number of
 * {@link ProxyConnection} and handles operations with them.
 * 
 */
public final class ConnectionPool {

    /** The Constant INIT_TIMEOUT. */
    private static final long INIT_TIMEOUT = 100;

    /** The Constant CHECK_TIMEOUT. */
    private static final int CHECK_TIMEOUT = 30;

    /** The Constant DEFAULT_POOL_SIZE. */
    private static final int DEFAULT_POOL_SIZE = 7;

    /** The instance. */
    private static ConnectionPool instance = null;

    /** The instance created. */
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);

    /** The pool initialised. */
    private static AtomicBoolean poolInitialised = new AtomicBoolean(false);

    /** The instance lock. */
    private static ReentrantLock instanceLock = new ReentrantLock();

    /** The driver name. */
    private String driverName;

    /** The url. */
    private String url;

    /** The user. */
    private String user;

    /** The password. */
    private String password;

    /** The pool size. */
    private int poolSize;

    /** The free connection queue. */
    private ArrayBlockingQueue<ProxyConnection> freeConnectionQueue;

    /** The given connection queue. */
    private ArrayBlockingQueue<ProxyConnection> givenConnectionQueue;

    /** The Constant LOG. */
    private static final Logger LOG = LogManager
            .getLogger(ConnectionPool.class);

    /**
     * Constructs the instantiates a new connection pool. It handles the
     * registration of the specified driver.
     */
    private ConnectionPool() {
        DBResourceManager dbResourseManager = DBResourceManager.getInstance();
        this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourseManager.getValue(DBParameter.DB_URL);
        this.user = dbResourseManager.getValue(DBParameter.DB_USER);
        this.password = dbResourseManager.getValue(DBParameter.DB_PASS);
        try {
            this.poolSize = Integer.parseInt(
                    dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
            LOG.log(Level.ERROR, "Incorrect data of pool size: " + e
                    + " Was used default size = " + DEFAULT_POOL_SIZE);
        }
        if (driverName == null || driverName.isEmpty() || url == null
                || url.isEmpty() || user == null || user.isEmpty()
                || password == null || password.isEmpty()) {
            LOG.log(Level.FATAL,
                    "Incorrect data -> driverName = " + driverName + ", url = "
                            + url + ", user = " + user + ", password = "
                            + password);
            throw new RuntimeException("Incorrect data -> driverName = "
                    + driverName + ", url = " + url + ", user = " + user
                    + ", password = " + password);
        }
        try {
            DriverManager.registerDriver(
                    (Driver) Class.forName(driverName).newInstance());
        } catch (SQLException | InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            LOG.log(Level.FATAL,
                    "Driver " + driverName + " wasn't registered: " + e);
            throw new RuntimeException(
                    "Driver " + driverName + " wasn't registered: " + e);
        }
        freeConnectionQueue = new ArrayBlockingQueue<>(poolSize);
        givenConnectionQueue = new ArrayBlockingQueue<>(poolSize);
    }

    /**
     * The method gets the single instance of the ConnectionPool, the instance
     * locked during the first access.
     * 
     * @return single instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            instanceLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    /**
     * The method handles creation of the specified number of
     * {@link ProxyConnection}.
     *
     * @throws ConnectionPoolException
     *             the connection pool exception
     */
    public void initPool() throws ConnectionPoolException {
        while (freeConnectionQueue.size() < poolSize) {
            try {
                ProxyConnection connection = new ProxyConnection(url, user,
                        password);
                freeConnectionQueue.put(connection);
            } catch (InterruptedException | SQLException e) {
                throw new ConnectionPoolException(
                        "SQLEx. or Interruped ex. in initPool", e);
            }
        }
        LOG.log(Level.INFO, "Connection pool was created with size = "
                + freeConnectionQueue.size());
        poolInitialised.set(true);
    }

    /**
     * The method takes one connection from the created connection pool. If the
     * connection is broken the method will call releaseConnection(connection)
     * to release connection into the pool and take next one.
     *
     * @return the proxy connection
     * @throws ConnectionPoolException
     *             the connection pool exception
     */
    public ProxyConnection takeConnection() throws ConnectionPoolException {
        checkInitialisation();
        ProxyConnection connection = null;
        boolean isTaken = false;
        while (!isTaken) {
            try {
                connection = freeConnectionQueue.take();
                if (connection == null || !connection.check(CHECK_TIMEOUT)) {
                    releaseConnection(connection);
                    LOG.log(Level.ERROR, "Taken connection had problem");
                } else {
                    isTaken = true;
                }
            } catch (InterruptedException | SQLException e) {
                throw new ConnectionPoolException(
                        "SQLEx. or Interruped ex. in takeConnection", e);
            }
        }
        boolean isPutted = false;
        boolean isInterupted = false;
        try {
            while (!isPutted) {
                try {
                    givenConnectionQueue.put(connection);
                    isPutted = true;
                    isInterupted = false;
                } catch (InterruptedException e) {
                    isPutted = false;
                    isInterupted = true;
                }
            }
        } finally {
            if (isInterupted)
                Thread.currentThread().interrupt();
        }
        LOG.log(Level.INFO, "\nConnection " + connection
                + " was taken\nfreeConnectionQueue size = "
                + freeConnectionQueue.size() + "\ngivenConnectionQueue size = "
                + givenConnectionQueue.size());
        return connection;
    }

    /**
     * The method checks initialization of the pool.
     */
    private void checkInitialisation() {
        boolean isInitChecked = false;
        boolean isCheckingInterupted = false;
        try {
            while (!poolInitialised.get() && !isInitChecked) {
                try {
                    TimeUnit.MILLISECONDS.sleep(INIT_TIMEOUT);
                    isInitChecked = true;
                    isCheckingInterupted = false;
                } catch (InterruptedException e) {
                    isInitChecked = false;
                    isCheckingInterupted = true;
                }
            }
        } finally {
            if (isCheckingInterupted)
                Thread.currentThread().interrupt();
        }
    }

    /**
     * The method releases connection into the pool. If the connection is broken
     * the method will create new one.
     *
     * @param connection
     *            the connection
     */
    public void releaseConnection(ProxyConnection connection) {
        checkInitialisation();
        boolean isRemoved = false;
        while (!isRemoved) {
            isRemoved = givenConnectionQueue.remove(connection);
        }
        boolean isBroken = false;
        try {
            if (connection == null || !connection.check(CHECK_TIMEOUT)) {
                isBroken = true;
                if (connection != null) {
                    connection.destroy();
                }
                connection = new ProxyConnection(url, user, password);
                LOG.log(Level.ERROR,
                        "Connection was lost, then was created new");
            }
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "SQLEx.: " + e);
        }
        try {
            if (!isBroken && !connection.getAutoCommit()) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
            freeConnectionQueue.put(connection);
        } catch (SQLException | InterruptedException e) {
            LOG.log(Level.ERROR, "SQLEx. or interruped ex.: " + e);
        }
        LOG.log(Level.INFO, "\nConnection " + connection
                + " was relised\nfreeConnectionQueue size = "
                + freeConnectionQueue.size() + "\ngivenConnectionQueue size = "
                + givenConnectionQueue.size());
    }

    /**
     * The method destroys the pool of connection.
     */
    public void destroyPool() {
        checkInitialisation();
        int counter = poolSize;
        while (counter != 0) {
            try {
                ProxyConnection connection = freeConnectionQueue.take();
                if (connection != null) {
                    connection.destroy();
                }
            } catch (SQLException | InterruptedException e) {
                LOG.log(Level.ERROR, "SQLEx. or interruped ex.: " + e);
            }
            counter--;
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOG.log(Level.ERROR, e + " DriverManager wasn't found.");
        } finally {
            instance = null;
            poolInitialised.set(false);
            instanceCreated.set(false);
            LOG.log(Level.INFO,
                    "Pool was destroyed. Existing connection number = "
                            + (givenConnectionQueue.size()
                                    + freeConnectionQueue.size()));
        }
    }
}

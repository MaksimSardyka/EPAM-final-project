package by.epam.auction.dao.pool;

/**
 * Pool layer Exception.
 */
public class ConnectionPoolException extends Exception {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = -2830438747232890197L;

    /**
     * Instantiates a new connection pool exception.
     */
    public ConnectionPoolException() {
    }

    /**
     * Instantiates a new connection pool exception.
     *
     * @param message the message
     */
    public ConnectionPoolException(String message) {
        super(message);
    }

    /**
     * Instantiates a new connection pool exception.
     *
     * @param message the message
     * @param e the e
     */
    public ConnectionPoolException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Instantiates a new connection pool exception.
     *
     * @param e the e
     */
    public ConnectionPoolException(Throwable e) {
        super(e);
    }
}

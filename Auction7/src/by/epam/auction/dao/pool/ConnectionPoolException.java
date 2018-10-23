package by.epam.auction.dao.pool;

/**
 * Pool layer Exception
 */
public class ConnectionPoolException extends Exception {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2830438747232890197L;

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable e) {
        super(message, e);
    }

    public ConnectionPoolException(Throwable e) {
        super(e);
    }
}

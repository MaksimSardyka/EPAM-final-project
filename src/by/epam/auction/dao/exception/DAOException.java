package by.epam.auction.dao.exception;

/**
 * DAO level exception.
 */
public class DAOException extends Exception{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8438761954663023095L;

    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

}

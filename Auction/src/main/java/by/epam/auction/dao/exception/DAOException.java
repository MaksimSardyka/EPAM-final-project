package by.epam.auction.dao.exception;

/**
 * DAO level exception.
 */
public class DAOException extends Exception{
    
    /** serialVersionUID. */
    private static final long serialVersionUID = -8438761954663023095L;

    /**
     * Instantiates a new DAO exception.
     */
    public DAOException() {
        super();
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param message the message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Instantiates a new DAO exception.
     *
     * @param cause the cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

}

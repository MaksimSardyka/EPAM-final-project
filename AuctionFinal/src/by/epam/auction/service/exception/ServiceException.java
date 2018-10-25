package by.epam.auction.service.exception;

/**
 * Service layer exception.
 */
public class ServiceException extends Exception{

    /** serialVersionUID. */
    private static final long serialVersionUID = 3823823708398432132L;

    /**
     * Instantiates a new service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new service exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new service exception.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

}

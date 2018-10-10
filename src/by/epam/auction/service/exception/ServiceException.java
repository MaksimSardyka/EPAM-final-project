package by.epam.auction.service.exception;

/**
 * Service layer exception.
 */
public class ServiceException extends Exception{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3823823708398432132L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}

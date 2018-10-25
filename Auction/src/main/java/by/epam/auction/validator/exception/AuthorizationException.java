package by.epam.auction.validator.exception;

/**
 * The Class AuthorizationException.
 */
public class AuthorizationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3445937964971616673L;

	/**
	 * Instantiates a new authorization exception.
	 */
	public AuthorizationException() {
		super();
	}

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param message the message
	 */
	public AuthorizationException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new authorization exception.
	 *
	 * @param cause the cause
	 */
	public AuthorizationException(Throwable cause) {
		super(cause);
	}

}

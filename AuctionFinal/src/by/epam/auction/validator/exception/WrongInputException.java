package by.epam.auction.validator.exception;

/**
 * The Class WrongInputException.
 */
public class WrongInputException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8221949487076898008L;

	/**
	 * Instantiates a new wrong input exception.
	 */
	public WrongInputException() {
		super();
	}

	/**
	 * Instantiates a new wrong input exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public WrongInputException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new wrong input exception.
	 *
	 * @param message the message
	 */
	public WrongInputException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new wrong input exception.
	 *
	 * @param cause the cause
	 */
	public WrongInputException(Throwable cause) {
		super(cause);
	}

}

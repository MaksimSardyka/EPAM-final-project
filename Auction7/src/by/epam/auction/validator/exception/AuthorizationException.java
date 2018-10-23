package by.epam.auction.validator.exception;

public class AuthorizationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3445937964971616673L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

}

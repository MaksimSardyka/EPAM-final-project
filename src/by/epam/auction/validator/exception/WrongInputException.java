package by.epam.auction.validator.exception;

public class WrongInputException extends Exception{

	private static final long serialVersionUID = 8221949487076898008L;

	public WrongInputException() {
		super();
	}

	public WrongInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongInputException(String message) {
		super(message);
	}

	public WrongInputException(Throwable cause) {
		super(cause);
	}

}

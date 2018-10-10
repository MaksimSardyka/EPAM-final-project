package by.epam.auction.command.exception;

public class CommandException extends Exception{
	private static final long serialVersionUID = 8526536198254037792L;

	public CommandException() {
		super();
	}

	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandException(String message) {
		super(message);
	}

	public CommandException(Throwable cause) {
		super(cause);
	}
}

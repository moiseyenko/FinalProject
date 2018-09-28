package by.epam.hotel.exception;

public class CommandException extends Exception {
	
	private static final long serialVersionUID = 1420227387206538815L;

	public CommandException() {
		super();
	}

	public CommandException( String message, Throwable exception) {
		super(message, exception);
	}

	public CommandException(Throwable exception) {
		super(exception);
	}

	public CommandException(String message) {
		super(message);
	}
}

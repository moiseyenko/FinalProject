package by.epam.hotel.exception;

/**
 * The class {@code CommandException} is subclass of {@code Exception}. Class
 * represent an exception that may occur on a command layer of the application
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class CommandException extends Exception {

	private static final long serialVersionUID = 1420227387206538815L;

	public CommandException() {
		super();
	}

	public CommandException(String message, Throwable exception) {
		super(message, exception);
	}

	public CommandException(Throwable exception) {
		super(exception);
	}

	public CommandException(String message) {
		super(message);
	}
}

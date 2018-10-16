package by.epam.hotel.exception;

/**
 * The class {@code MailException} is subclass of {@code Exception}. Class
 * represent an exception that may occur when sending an email
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class MailException extends Exception {

	private static final long serialVersionUID = 404683131861925789L;

	public MailException() {
		super();
	}

	public MailException(String message, Throwable exception) {
		super(message, exception);
	}

	public MailException(Throwable exception) {
		super(exception);
	}

	public MailException(String message) {
		super(message);
	}
}

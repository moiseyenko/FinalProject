package by.epam.hotel.util;

public class MailException extends Exception{
	
	private static final long serialVersionUID = 404683131861925789L;

	public MailException() {
		super();
	}

	public MailException( String message, Throwable exception) {
		super(message, exception);
	}

	public MailException(Throwable exception) {
		super(exception);
	}

	public MailException(String message) {
		super(message);
	}
}

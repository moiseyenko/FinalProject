package by.epam.hotel.exception;

/**
 * The class {@code ServiceException} is subclass of {@code Exception}. Class
 * represent an exception that may occur on a service layer of the application
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 212036281445967643L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable exception) {
		super(message, exception);
	}

	public ServiceException(Throwable exception) {
		super(exception);
	}

	public ServiceException(String message) {
		super(message);
	}
}

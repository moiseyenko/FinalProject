package by.epam.hotel.exception;

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

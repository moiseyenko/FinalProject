package by.epam.hotel.exception;

public class CloseTransactionException extends Exception {
	
	private static final long serialVersionUID = 1760514890877369129L;

	public CloseTransactionException() {
		super();
	}

	public CloseTransactionException( String message, Throwable exception) {
		super(message, exception);
	}

	public CloseTransactionException(Throwable exception) {
		super(exception);
	}

	public CloseTransactionException(String message) {
		super(message);
	}
}

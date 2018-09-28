package by.epam.hotel.exception;

public class DaoException extends Exception {

	private static final long serialVersionUID = 5340859232861460811L;

	public DaoException() {
		super();
	}

	public DaoException( String message, Throwable exception) {
		super(message, exception);
	}

	public DaoException(Throwable exception) {
		super(exception);
	}

	public DaoException(String message) {
		super(message);
	}
}

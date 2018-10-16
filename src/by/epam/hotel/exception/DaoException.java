package by.epam.hotel.exception;

/**
 * The class {@code DaoException} is subclass of {@code Exception}. Class
 * represent an exception that may occur on a Dao layer of the application
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
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

package by.epam.hotel.dao;

import java.sql.Connection;
import java.util.List;

import by.epam.hotel.entity.Entity;
import by.epam.hotel.exception.DaoException;

/**
 * The class is the top of the DAO hierarchy with a description common methods
 * that will be used when interacting with a table or a group of tables.
 * 
 * @author Evgeniy Moiseyenko
 *
 * @param <K> entity id
 * @param <T> subclass of {@link by.epam.hotel.entity.Entity Entity} that
 *        represents a specific entry from a database table.
 */
public abstract class AbstractDao<K, T extends Entity> {
	protected Connection connection;

	/**
	 * The method returns list of records of specified entity. Returned list is
	 * limited by recordsPerPage
	 * 
	 * 
	 * @param start          top records of dispayed list of specified entity
	 * @param recordsPerPage number of records displayed on page
	 * @return list of records of specified entity
	 * @throws DaoException if method has catched {@link java.sql.SQLException
	 *                      SQLException}
	 */
	public abstract List<T> findAll(int start, int recordsPerPage) throws DaoException;

	/**
	 * The method returns specified entity by id.
	 * 
	 * 
	 * @param id entity id
	 * @return specified entity by id
	 * @throws DaoException if method has catched {@link java.sql.SQLException
	 *                      SQLException}
	 */
	public abstract T findEntityById(K id) throws DaoException;

	/**
	 * The method create new record in database table
	 * 
	 * 
	 * @param entity entity that specifies creation of new records in database table.
	 * @return <tt>true</tt> if new row in database table was created successfully.
	 * @throws DaoException if method has catched {@link java.sql.SQLException
	 *                      SQLException}
	 */
	public abstract boolean create(T entity) throws DaoException;

	/**
	 * The method update specified record in database table
	 * 
	 * 
	 * @param entity that will be updated in database table
	 * @return <tt>true</tt> if row in database table corresponding to the entity was updated successfully.
	 * @throws DaoException if method has catched {@link java.sql.SQLException
	 *                      SQLException}
	 */
	public abstract boolean update(T entity) throws DaoException;

	/**
	 * The method simulates deletion and restoration of records in the database
	 * 
	 * 
	 * @param entity that will be deleted or restore in database
	 * @return <tt>true</tt> if row in database table corresponding to the entity was deleted or restored successfully.
	 * @throws DaoException if method has catched {@link java.sql.SQLException
	 *                      SQLException}
	 */
	public abstract boolean changeRemoved(T entity) throws DaoException;

	/**
	 * The method is used to provide Dao layer with connection
	 * 
	 * 
	 * @param connection that connect database and application for execution operation on database
	 */
	void setConnection(Connection connection) {
		this.connection = connection;
	}
}

package by.epam.hotel.dao;

import java.sql.Connection;
import java.util.List;

import by.epam.hotel.entity.Entity;
import by.epam.hotel.exception.DaoException;

public abstract class AbstractDao<K, T extends Entity> {
	protected Connection connection;

	public abstract List<T> findAll(int start, int recordsPerPage) throws DaoException;

	public abstract T findEntityById(K id) throws DaoException;

	public abstract boolean delete(K id) throws DaoException;

	public abstract boolean delete(T entity) throws DaoException;

	public abstract boolean create(T entity) throws DaoException;

	public abstract boolean update(T entity) throws DaoException;

	public abstract boolean changeRemoved(T entity) throws DaoException;

	void setConnection(Connection connection) {
		this.connection = connection;
	}
}

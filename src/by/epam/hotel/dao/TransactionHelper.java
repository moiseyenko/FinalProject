package by.epam.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.pool.ConnectionPool;

/**
 * Class {@link TransactionHelper} is used to provide operations with database
 * by dao objects
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class TransactionHelper implements AutoCloseable {
	private static final Logger LOG = LogManager.getLogger();
	private Connection connection = ConnectionPool.getInstance().getConnection();
	private List<AbstractDao<?, ?>> daoList = new LinkedList<>();

	/**
	 * This method is used to provide operations with database
	 * by several dao objects at one transaction.
	 * 
	 * 
	 * @param an array of dao objects performing database operations
	 */
	public void doTransaction(AbstractDao<?, ?>... daos) {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error(exc);
			}
		}
		for (AbstractDao<?, ?> daoEntity : daos) {
			daoEntity.setConnection(connection);
			daoList.add(daoEntity);
		}
	}

	/**
	 * This method is used to provide operations with database
	 * by single dao object at one transaction.
	 * 
	 * 
	 * @param dao object performing database operations
	 */
	public void doOperation(AbstractDao<?, ?> dao) {
		dao.setConnection(connection);
		daoList.add(dao);
	}

	@Override
	public void close() {
		for (AbstractDao<?, ?> dao : daoList) {
			dao.setConnection(null);
		}
		daoList.clear();
		ConnectionPool.getInstance().releaseConnection(connection);
	}

	/**
	 * This method is used to commit changes made
	 */
	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error(exc);
			}
		}
	}

	/**
	 * This method is used to rollback changes made
	 */
	public void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error(exc);
			}
		}
	}
}

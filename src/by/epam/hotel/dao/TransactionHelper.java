package by.epam.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.pool.ConnectionPool;

public class TransactionHelper implements AutoCloseable {
	private static final Logger LOG = LogManager.getLogger();
	private Connection connection = ConnectionPool.getInstance().getConnection();
	private List<AbstractDao<?, ?>> daoList = new LinkedList<>();

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

	public void doOperation(AbstractDao<?, ?> dao) {
		dao.setConnection(connection);
		daoList.add(dao);
	}

	@Override
	public void close() throws CloseTransactionException {
		for (AbstractDao<?, ?> dao : daoList) {
			dao.setConnection(null);
		}
		daoList.clear();
		ConnectionPool.getInstance().releaseConnection(connection);
	}

	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			for (Throwable exc : e) {
				LOG.error(exc);
			}
		}
	}

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

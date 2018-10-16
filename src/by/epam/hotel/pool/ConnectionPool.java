package by.epam.hotel.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class {@code ConnectionPool} is an implementation of thread-safe pool.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class ConnectionPool implements Cloneable {
	private static final Logger LOG = LogManager.getLogger();

	private final int DEFUALT_POOL_SIZE = 10;

	private BlockingQueue<ProxyConnection> blockingConnections;
	private BlockingQueue<ProxyConnection> usedConnections;
	private int poolSize;

	private static ConnectionPool instance;
	private static AtomicBoolean created = new AtomicBoolean(false);
	private static Lock lock = new ReentrantLock();

	/**
	 * Initialize connection pool instance, thereby creating the specified set of
	 * proxy connections for working with the database
	 */
	private ConnectionPool() {
		if (instance != null) {
			throw new RuntimeException("Connection pool has already been created");
		}
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			LOG.fatal("Database access error occurs: {}", e);
			throw new RuntimeException("Database access error occurs: {}", e);

		}

		blockingConnections = new LinkedBlockingQueue<>();
		usedConnections = new LinkedBlockingQueue<>();

		String stringPoolSize = DatabaseManager.getPoolSize();
		if (stringPoolSize != null) {
			poolSize = Integer.parseInt(stringPoolSize);
		} else {
			poolSize = DEFUALT_POOL_SIZE;
		}

		// try to create specified number of connections
		for (int i = 0; i < poolSize; i++) {
			try {
				Connection connection = DriverManager.getConnection(DatabaseManager.getUrl(),
						DatabaseManager.getProperties());
				ProxyConnection proxyConnection = new ProxyConnection(connection);
				blockingConnections.put(proxyConnection);
				LOG.debug(connection + " is created");
			} catch (InterruptedException e) {
				LOG.error("Interrupted while waiting: {}", e);
				Thread.currentThread().interrupt();
			} catch (SQLException e) {
				LOG.error("Database access error occurs: {}", e);
			}
		}

		// if specified number of connections has not been created, try create missing
		// connections again.
		// if this time fails to create connections, a RuntimeException is thrown
		if (blockingConnections.size() < poolSize) {
			for (int i = 0; i < Math.subtractExact(blockingConnections.size(), poolSize); i++) {
				try {
					Connection connection = DriverManager.getConnection(DatabaseManager.getUrl(),
							DatabaseManager.getProperties());
					ProxyConnection proxyConnection = new ProxyConnection(connection);
					blockingConnections.put(proxyConnection);
					LOG.debug(connection + " is created");
				} catch (InterruptedException e) {
					LOG.fatal("Interrupted while waiting: {}", e);
					Thread.currentThread().interrupt();
					throw new RuntimeException("Interrupted while waiting: {}", e);
				} catch (SQLException e) {
					LOG.fatal("Database access error occurs: {}", e);
					throw new RuntimeException("Database access error occurs: {}", e);
				}
			}
		}

	}

	/**
	 * This method is used to get {@link by.epam.hotel.pool.ConnectionPool
	 * ConnectionPool} instance
	 * 
	 * 
	 * @return {@link by.epam.hotel.pool.ConnectionPool ConnectionPool} instance
	 */
	public static ConnectionPool getInstance() {
		if (!created.get()) {
			lock.lock();
			try {
				if (instance == null) {
					instance = new ConnectionPool();
					created.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	/**
	 * This method is used to prevent clone of
	 * {@link by.epam.hotel.pool.ConnectionPool ConnectionPool}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * This method is used to retrieve {@link java.sql.Connection Connection} that
	 * is wrapped in a {@link by.epam.hotel.pool.ProxyConnection ProxyConnection}
	 * from blockingConnections queue and move specified connection to
	 * usedConnections queue. If blockingConnections queue is empty, method waits
	 * until a connection becomes available.
	 * 
	 * 
	 * @return {@link java.sql.Connection Connection} that is wrapped in a
	 *         {@link by.epam.hotel.pool.ProxyConnection ProxyConnection}
	 */
	public Connection getConnection() {
		ProxyConnection connection = null;
		try {
			connection = blockingConnections.take();
			usedConnections.put(connection);
			LOG.debug(connection + " got FROM pool");
		} catch (InterruptedException e) {
			LOG.error("Interrupted while waiting: {}", e);
			Thread.currentThread().interrupt();
			throw new RuntimeException("Interrupted while waiting: {}", e);
		}
		return connection;
	}

	/**
	 * The method is used to return connection to blockingConnections queue removing
	 * it from usedConnections queue. Also sets auto commit equals true.
	 * 
	 * 
	 * @param connection that will be return to blockingConnections queue
	 */
	public void releaseConnection(Connection connection) {
		LOG.debug(connection);
		if (connection != null && connection.getClass() == ProxyConnection.class) {
			try {
				usedConnections.remove(connection);
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					for (Throwable exc : e) {
						LOG.error(exc);
					}
				}
				blockingConnections.put((ProxyConnection) connection);
				LOG.debug(connection + " returned IN pool");
			} catch (InterruptedException e) {
				LOG.error("Interrupted while waiting: {}", e);
			}
		}
	}

	/**
	 * The method is user to close all connections from Connection pool and deregist
	 * all drivers
	 */
	public void destroyPool() {
		System.out.println(poolSize);
		for (int i = 0; i < poolSize; i++) {
			try {
				((ProxyConnection) blockingConnections.take()).closeConnection();
			} catch (SQLException e) {
				LOG.error("Database access error occurs: {}", e);
			} catch (InterruptedException e) {
				LOG.error("Interrupted while waiting: {}", e);
				Thread.currentThread().interrupt();
			}
			LOG.debug("Connection closed");
		}
		deregisterDrivers();
	}

	private static void deregisterDrivers() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				LOG.error("Database access error occurs: {}", e);
			}
		}
	}
}

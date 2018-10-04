package by.epam.hotel.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool implements Cloneable {
	private static final Logger LOG = LogManager.getLogger();
	private final int DEFUALT_POOL_SIZE = 10;
	private LinkedBlockingQueue<ProxyConnection> blockingConnections;
	private LinkedBlockingQueue<ProxyConnection> usedConnections;
	private int poolSize;

	private static ConnectionPool instance;
	private static AtomicBoolean created = new AtomicBoolean(false);
	private static ReentrantLock lock = new ReentrantLock();

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
		for (int i = 0; i < poolSize; i++) {
			try {
				Connection connection = DriverManager.getConnection(DatabaseManager.getUrl(),
						DatabaseManager.getProperties());
				ProxyConnection proxyConnection = new ProxyConnection(connection);
				blockingConnections.put(proxyConnection);
			} catch (InterruptedException e) {
				LOG.error("Interrupted while waiting: {}", e);
				Thread.currentThread().interrupt();
			} catch (SQLException e) {
				LOG.error("Database access error occurs: {}", e);
			}
		}

		if (blockingConnections.size() < poolSize) {
			for (int i = 0; i < Math.subtractExact(blockingConnections.size(), poolSize); i++) {
				try {
					Connection connection = DriverManager.getConnection(DatabaseManager.getUrl(),
							DatabaseManager.getProperties());
					ProxyConnection proxyConnection = new ProxyConnection(connection);
					blockingConnections.put(proxyConnection);
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

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

	public void releaseConnection(Connection connection) {
		LOG.debug(connection + " : " + connection != null);
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

	public void destroyPool() {
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

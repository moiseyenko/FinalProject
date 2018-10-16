package by.epam.hotel.pool;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Class {@link DatabaseManager} is used to provide {@link ConnectionPool} with
 * database data for creation connections.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class DatabaseManager {
	private static final String PROPS_PATH = "resource.db";
	private static ResourceBundle resource = ResourceBundle.getBundle(PROPS_PATH);

	public static Properties getProperties() {
		Properties properties = new Properties();
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}
		return properties;
	}

	/**
	 * The method returns the number of connections that should be created in the
	 * connection pool.
	 * 
	 * 
	 * @return the number of connections that should be created in the connection
	 *         pool.
	 */
	public static String getPoolSize() {
		return resource.getString("poolSize");
	}

	/**
	 * The method returns the database url.
	 * 
	 * 
	 * @return the database url
	 */
	public static String getUrl() {
		return resource.getString("url");

	}
}

package by.epam.hotel.pool;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

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
	
	public static String getPoolSize () {
		return resource.getString("poolSize");
	}
	
	public static String getUrl () {
		return resource.getString("url");
		
	}
}

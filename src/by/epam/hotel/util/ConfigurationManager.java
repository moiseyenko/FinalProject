package by.epam.hotel.util;

import java.util.ResourceBundle;

/**
 * Class {@link ConfigurationManager} is used to provide appication with paths to jsp.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class ConfigurationManager {
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.config");

	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}

}

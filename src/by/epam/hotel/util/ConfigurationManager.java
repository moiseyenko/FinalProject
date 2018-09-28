package by.epam.hotel.util;

import java.util.ResourceBundle;

public class ConfigurationManager {
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.config");

	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}

}

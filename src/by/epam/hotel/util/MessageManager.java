package by.epam.hotel.util;

import java.util.ResourceBundle;

public class MessageManager {
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resource.messages");

	private MessageManager() {
	}

	public static Object getProrerty(String key) {
		return resourceBundle.getString(key);
	}

}

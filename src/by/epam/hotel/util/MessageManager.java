package by.epam.hotel.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class {@link MessageManager} is used to provide application with different info message.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class MessageManager {
	private static final Locale ENG = new Locale("en", "US");
	private static final Locale RUS = new Locale("ru", "RU");
	private static final ResourceBundle resourceBundleEn = ResourceBundle.getBundle("resource.i18n.messages", ENG);
	private static final ResourceBundle resourceBundleRu = ResourceBundle.getBundle("resource.i18n.messages", RUS);

	private MessageManager() {
	}

	public static String getProrerty(String key, Locale locale) {
		if(RUS.equals(locale)) {
			return resourceBundleRu.getString(key);
		}else {
			return resourceBundleEn.getString(key);
		}
		
	}

}

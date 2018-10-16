package by.epam.hotel.util.type;

import java.util.Locale;

/**
 * {@link by.epam.hotel.util.type.LanguageType LanguageType} is used to provide internationalization of application by 
 * return Russian or English {@link java.util.Locale.Locale Locale} object
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public enum LanguageType {
	RUSSIAN(new Locale("ru", "RU")), ENGLISH(new Locale("en", "US"));
	
	private Locale value;
	
	private LanguageType(Locale value) {
		this.value = value;
	}
	
	public Locale getValue() {
		return value;
	}
}

package by.epam.hotel.util.apptype;

import java.util.Locale;

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

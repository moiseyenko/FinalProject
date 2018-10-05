package by.epam.hotel.util.apptype;

public enum LanguageType {
	RUSSIAN("ru_RU"), ENGLISH("en_US");
	
	private String value;
	
	private LanguageType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getCountry() {
		return value.substring(3);
	}
	
	public String getLanguage() {
		return value.substring(0, 2);
	}
}

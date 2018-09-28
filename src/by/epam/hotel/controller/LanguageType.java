package by.epam.hotel.controller;

public enum LanguageType {
	RUSSIAN("ru_RU"), ENGLISH("en_US");
	
	private String value;
	
	private LanguageType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

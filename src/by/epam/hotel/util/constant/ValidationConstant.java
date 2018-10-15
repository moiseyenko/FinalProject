package by.epam.hotel.util.constant;

public interface ValidationConstant {
	String LOGIN_PATTERN = "^[a-zA-ZÀ-ßà-ÿ0-9_-]{3,25}$";
	String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zà-ÿ])(?=.*[A-ZÀ-ß])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	String EMAIL_PATTERN = "^[A-Za-z0-9_.%+-]{1,30}@[A-Za-z0-9.-]{1,10}\\.[A-Za-z]{2,6}$";
	String COUNTRY_PATTERN = "^[A-Za-zÀ-ÿà-ÿ ¨¸'-]{1,80}$";
	String CAPACITY_PATTERN = "^[0-9]{1,5}$";
	String PRICE_PATTERN = "^\\d{1,10}([\\.,]\\d{0,2})?$";
	String COMMA = ",";
	String DOT = ".";
	String CLASS_ID_PATTERN = "^[A-Za-zÀ-ÿà-ÿ0-9 ¨¸'-]{1,25}$";
	String COUNTRY_ID_PATTERN = "^[A-Z][A-Z]$";
	String NUMBER_PATTERN = "^[0-9]{1,5}$";
	String FIRST_NAME_PATTERN = "^[A-ZÀ-ß¨][a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,44}$";
	String LAST_NAME_PATTERN = "^[a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,45}$";
	String PASSPORT_PATTERN = "^[a-zA-Z0-9]{1,15}$";
	String EMPTY_STRING = "";
	String CONFIRMATION_KEY = "^[0-9a-f]{10}$";
	
}

package by.epam.hotel.util.constant;

public interface ValidationConstant {
	String LOGIN_PATTERN = "^[a-zA-ZÀ-ßà-ÿ0-9_-]{3,25}$";
	/*
	 * ^ # start-of-string (?=.*[0-9]) # a digit must occur at least once
	 * (?=.*[a-z]) # a lower case letter must occur at least once (?=.*[A-Z]) # an
	 * upper case letter must occur at least once (?=.*[@#$%^&+=]) # a special
	 * character must occur at least once (?=\S+$) # no whitespace allowed in the
	 * entire string .{8,} # anything, at least eight places though $ #
	 * end-of-string
	 */
	String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zà-ÿ])(?=.*[A-ZÀ-ß])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	String EMAIL_PATTERN = "^[A-Z0-9_.%+-]{1,30}@[A-Z0-9.-]{1,10}\\.[A-Z]{2,6}$";
	String COUNTRY_PATTERN = "^[A-Za-zÀ-ÿà-ÿ ¨¸'-]{1,80}$";
	String CAPACITY_PATTERN = "^[0-9]{1,5}$";
	String PRICE_PATTERN = "^[0-9]{1,10}.[0-9]{0,2}$";
	String COMMA = ",";
	String DOT = ".";
	String CLASS_ID_PATTERN = "^[A-Za-zÀ-ÿà-ÿ0-9 ¨¸'-]{1,25}$";
	String COUNTRY_ID_PATTERN = "^[A-Z][A-Z]$";
	String NUMBER_PATTERN = "^[0-9]{1,5}$";
	String FIRST_NAME_PATTERN = "^[A-ZÀ-ß¨][a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,44}$";
	String LAST_NAME_PATTERN = "^[a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,45}$";
	String PASSPORT_PATTERN = "^[a-zA-Z0-9]{1,15}$";
	String EMPTY_STRING = "";
	
}

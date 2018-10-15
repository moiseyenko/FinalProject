package by.epam.hotel.util.validator;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.hotel.util.constant.ValidationConstant;

public class RoomValidator {
	
	public static boolean validateNumber(String number) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(ValidationConstant.NUMBER_PATTERN);
		Matcher matcher = pattern.matcher(number);
		if(matcher.matches()) {
			flag = Integer.parseInt(number)<=65535;
		}
		return flag;
	}
	
	public static boolean validateClassId(String classId) {
		Pattern pattern = Pattern.compile(ValidationConstant.CLASS_ID_PATTERN);
		Matcher matcher = pattern.matcher(classId);
		return matcher.matches();
	}
	
	public static boolean validateCurrency(String currency) {
		Pattern pattern = Pattern.compile(ValidationConstant.PRICE_PATTERN);
		Matcher matcher = pattern.matcher(currency);
		return matcher.matches();
	}
	
	public static boolean validateFromTo(String from, String to) {
		if (ValidationConstant.EMPTY_STRING.equals(from) || ValidationConstant.EMPTY_STRING.equals(to)) {
			return false;
		}
		LocalDate localDateFrom = LocalDate.parse(from);
		LocalDate localDateTo = LocalDate.parse(to);
		return (localDateFrom.isAfter(LocalDate.now())||localDateFrom.isEqual(LocalDate.now())) 
				&& (localDateFrom.isEqual(localDateTo)||localDateTo.isAfter(localDateFrom));
	}
}

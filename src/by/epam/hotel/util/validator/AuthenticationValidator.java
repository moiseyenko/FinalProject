package by.epam.hotel.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.hotel.util.constant.ValidationConstant;

public class AuthenticationValidator {
	public static boolean validateEmailConfirmationKey(String emailConfirmationKey) {
		Pattern pattern = Pattern.compile(ValidationConstant.CONFIRMATION_KEY);
		Matcher matcher = pattern.matcher(emailConfirmationKey);
		return matcher.matches();
	}
	
}

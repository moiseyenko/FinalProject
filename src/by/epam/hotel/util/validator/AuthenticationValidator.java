package by.epam.hotel.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.hotel.util.constant.ValidationConstant;

/**
 * Class {@link AuthenticationValidator} is used to provide validation of data
 * during authentication.
 * 
 * 
 * @author User
 *
 */
public class AuthenticationValidator {
	public static boolean validateEmailConfirmationKey(String emailConfirmationKey) {
		Pattern pattern = Pattern.compile(ValidationConstant.CONFIRMATION_KEY);
		Matcher matcher = pattern.matcher(emailConfirmationKey);
		return matcher.matches();
	}

}

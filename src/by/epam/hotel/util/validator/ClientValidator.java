package by.epam.hotel.util.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epam.hotel.entity.Nationality;
import by.epam.hotel.util.constant.ValidationConstant;

public class ClientValidator {
	public static boolean validateFName(String fname) {
		Pattern pattern = Pattern.compile(ValidationConstant.FIRST_NAME_PATTERN);
		Matcher matcher = pattern.matcher(fname);
		return matcher.matches();
	}

	public static boolean validateLName(String lname) {
		Pattern pattern = Pattern.compile(ValidationConstant.LAST_NAME_PATTERN);
		Matcher matcher = pattern.matcher(lname);
		return matcher.matches();
	}

	public static boolean validatePassport(String passport) {
		Pattern pattern = Pattern.compile(ValidationConstant.PASSPORT_PATTERN);
		Matcher matcher = pattern.matcher(passport);
		return matcher.matches();
	}

	public static boolean validateNationality(String tempNationalityId, List<Nationality> nationalities) {
		for (Nationality nationality : nationalities) {
			if (nationality.getCountryId().equals(tempNationalityId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateCountryId(String countryId) {
		Pattern pattern = Pattern.compile(ValidationConstant.COUNTRY_ID_PATTERN);
		Matcher matcher = pattern.matcher(countryId);
		return matcher.matches();
	}
	
	public static boolean validateCountry(String country) {
		Pattern pattern = Pattern.compile(ValidationConstant.COUNTRY_PATTERN);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}
}

package by.epam.hotel.util.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthenticationValidatorTest {
	// test valid confirmation key
	@Test(dataProvider = "validConfirmationKeys")
	public void validateEmailConfirmationKeyPositiveTest(String validConfirmationKey) {
		assertTrue(AuthenticationValidator.validateEmailConfirmationKey(validConfirmationKey));
	}

	// test invalid confirmation key
	@Test(dataProvider = "invalidConfirmationKeys")
	public void validateEmailConfirmationKeyNegativeTest(String invalidConfirmationKey) {
		assertFalse(AuthenticationValidator.validateEmailConfirmationKey(invalidConfirmationKey));
	}

	@DataProvider
	public Object[][] validConfirmationKeys() {
		return new Object[][] { new Object[] { "a2b4c6df10" }, new Object[] { "abcdef1234" },
				new Object[] { "97af2303f1" }, };
	}

	@DataProvider
	public Object[][] invalidConfirmationKeys() {
		return new Object[][] { new Object[] { "abcdefghi11" }, new Object[] { "AbCdefghij" },
				new Object[] { "abcdefg12Û" }, };
	}

}

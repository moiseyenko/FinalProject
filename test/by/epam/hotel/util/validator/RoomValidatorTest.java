package by.epam.hotel.util.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RoomValidatorTest {

	//test valid currency
	@Test(dataProvider = "validCurrencies")
	public void validateCurrencyPositiveTest (String validCurrency) {
		assertTrue(RoomValidator.validateCurrency(validCurrency));
	}
	
	//test invalid currency
	@Test(dataProvider = "invalidCurrencies")
	public void validateCurrencyNegativeTest (String invalidCurrency) {
		assertFalse(RoomValidator.validateCurrency(invalidCurrency));
	}
	
	@DataProvider
	public Object[][] validCurrencies() {
		return new Object[][] { 
			new Object[] { "22,33" },
			new Object[] { "22." },
			new Object[] { "1234567891.12" },
			};
	}
	
	@DataProvider
	public Object[][] invalidCurrencies() {
		return new Object[][] { 
			new Object[] { "12345678910" },
			new Object[] { "12.333" },
			new Object[] { "12:33" }, 
			};
	}
}

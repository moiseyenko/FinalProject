package by.epam.hotel.util.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AccountValidatorTest {
	// test valid login
	@Test(dataProvider = "validLogins")
	public void validateLoginPositiveTest(String validLogin) {
		assertTrue(AccountValidator.validateLogin(validLogin));
	}

	// test invalid login
	@Test(dataProvider = "invalidLogins")
	public void validateLoginNegativeTest(String invalidLogin) {
		assertFalse(AccountValidator.validateLogin(invalidLogin));
	}

	// test valid password
	@Test(dataProvider = "validPasswords")
	public void validatePasswordPositiveTest(String validPassword) {
		assertTrue(AccountValidator.validatePassword(validPassword));
	}

	// test invalid password
	@Test(dataProvider = "invalidPasswords")
	public void validatePasswordNegativeTest(String invalidPassword) {
		assertFalse(AccountValidator.validatePassword(invalidPassword));
	}

	// test valid email
	@Test(dataProvider = "validEmails")
	public void validateEmailPositiveTest(String validEmail) {
		assertTrue(AccountValidator.validateEmail(validEmail));
	}

	// test invalid email
	@Test(dataProvider = "invalidEmails")
	public void validateEmailNegativeTest(String invalidEmail) {
		assertFalse(AccountValidator.validateEmail(invalidEmail));
	}

	@DataProvider
	public Object[][] validLogins() {
		return new Object[][] { new Object[] { "log-3_" }, new Object[] { "Login_characters_Равен-25" }, };
	}

	@DataProvider
	public Object[][] invalidLogins() {
		return new Object[][] { new Object[] { "C2" }, new Object[] { "wrong_characters:*!" },
				new Object[] { "Login_characters_Равен_more_25" }, };
	}

	@DataProvider
	public Object[][] validPasswords() {
		return new Object[][] { new Object[] { "MoiseyenkoEA#1" }, new Object[] { "Moiseyenko@Evgeniy1992" },
				new Object[] { "123Моисеенко++" }, };
	}

	@DataProvider
	public Object[][] invalidPasswords() {
		return new Object[][] { new Object[] { "Меньше8" }, new Object[] { "No_spechial_Chars123" },
				new Object[] { "нет_заглавной-буквы@" }, };
	}

	@DataProvider
	public Object[][] validEmails() {
		return new Object[][] { new Object[] { "Moiseyenko_E-A@gmail.com" }, new Object[] { "moiseyenkoe.e.a@mail.ru" },
				new Object[] { "Moiseyenko_e_a.1992@EPAM.by" }, };
	}

	@DataProvider
	public Object[][] invalidEmails() {
		return new Object[][] { new Object[] { "только_латынь@mail.ru" }, new Object[] { "@moreThanTenChar.com" },
				new Object[] { "Moiseyenko.e.a@gmail.c" }, };
	}

}

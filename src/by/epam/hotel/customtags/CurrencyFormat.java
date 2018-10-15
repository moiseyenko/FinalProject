package by.epam.hotel.customtags;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrencyFormat {
	
	private final static Locale ruLocale = new Locale("ru", "RU");
	
	public static String parseCurrency (BigDecimal currency, Locale currentLocale) {
		String result;
		if(ruLocale.equals(currentLocale)) {
			currency = currency.multiply(new BigDecimal("2"));
		}
			result = NumberFormat.getCurrencyInstance(currentLocale).format(currency);

		return result;
	}
	
	
}

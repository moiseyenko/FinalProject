package by.epam.hotel.entity;

import java.time.LocalDate;

/**
 * Class {@link LocalDateNow} is used to provide current date and time in {@link LocalDate}
 * type.
 * 
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class LocalDateNow {
	private LocalDate now = LocalDate.now();

	public LocalDate getNow() {
		return now;
	}
}

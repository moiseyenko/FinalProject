package by.epam.hotel.dao.entity;

import java.time.LocalDate;

public class LocalDateNow {
	private LocalDate now = LocalDate.now();

	public LocalDate getNow() {
		return now;
	}
}

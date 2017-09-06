package itmo.visits_control.models;

import java.time.Duration;
import java.time.LocalDate;

public class DayInfo {
	private LocalDate date;
	/**
	 * Сколько надо отработать часов по плану
	 */
	private Duration fullRequiredHourse;
	/**
	 * Сколько отработано часов
	 */
	private Duration workedOutHours;
	
	private double rateSize=0;
}

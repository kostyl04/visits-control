package itmo.visits_control.models;

import java.time.LocalDate;

public interface Escape {
	EscapeType getType();

	boolean checkEscapeDate(LocalDate tempDate);
}

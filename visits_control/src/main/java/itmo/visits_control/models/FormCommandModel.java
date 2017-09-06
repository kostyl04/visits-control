package itmo.visits_control.models;

import java.util.List;

public class FormCommandModel {
	private List<String> jsonStrings;
	private int month;
	private int year;

	public List<String> getJsonStrings() {
		return jsonStrings;
	}

	public void setJsonStrings(List<String> jsonStrings) {
		this.jsonStrings = jsonStrings;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}

package model;

import java.math.BigDecimal;

public class cummulative {
	private String date;
	private BigDecimal value;
	String getDate() {
		return date;
	}
	void setDate(String date) {
		this.date = date;
	}
	BigDecimal getValue() {
		return value;
	}
	void setValue(BigDecimal value) {
		this.value = value;
	}
}

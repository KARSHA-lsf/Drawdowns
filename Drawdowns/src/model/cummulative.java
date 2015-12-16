package model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class cummulative {
	private String clmdate;
	private BigDecimal value;
	
	@Id
	public String getDate() {
		return clmdate;
	}
	public void setDate(String clmdate) {
		this.clmdate = clmdate;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
}

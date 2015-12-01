package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sys_CLM_EndofMonthLMC {

	private String lmcdate;
	private Double value;
	
	@Id
	public String getLmcdate() {
		return lmcdate;
	}
	public void setLmcdate(String lmcdate) {
		this.lmcdate = lmcdate;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}	
}

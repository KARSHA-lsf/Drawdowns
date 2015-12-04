package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CRSP_ValueWeightedReturns {
	private String Crsp_date;
	private Double Crsp_ret;
	private Double Crsp_value;
	
	@Id
	public String getCrsp_date() {
		return Crsp_date;
	}
	public void setCrsp_date(String crsp_date) {
		Crsp_date = crsp_date;
	}
	public Double getCrsp_ret() {
		return Crsp_ret;
	}
	public void setCrsp_ret(Double crsp_ret) {
		Crsp_ret = crsp_ret;
	}
	public Double getCrsp_value() {
		return Crsp_value;
	}
	public void setCrsp_value(Double crsp_value) {
		Crsp_value = crsp_value;
	}
	public int hashCode() {
		int result = 17;

		result = (int) (37 * result + this.getCrsp_ret());
		result = (int) (37 * result + this.getCrsp_value());
		return result;
	}
	

}
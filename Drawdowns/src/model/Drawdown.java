package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Drawdown {
	int permno;
	int yrmo;
	String drawdownDate;
	BigDecimal drawdownValue;
	double marketCapitalization;
	BigDecimal returnValue;
	public int getPermno() {
		return permno;
	}
	public void setPermno(int permno) {
		this.permno = permno;
	}
	public int getYrmo() {
		return yrmo;
	}
	public void setYrmo(int yrmo) {
		this.yrmo = yrmo;
	}
	public String getDrawdownDate() {
		return drawdownDate;
	}
	public void setDrawdownDate(String drawdownDate) {
		this.drawdownDate = drawdownDate;
	}
	public BigDecimal getDrawdownValue() {
		return drawdownValue;
	}
	public void setDrawdownValue(BigDecimal drawdownValue) {
		this.drawdownValue = drawdownValue;
	}
	public double getMarketCapitalization() {
		return marketCapitalization;
	}
	public void setMarketCapitalization(String marketCapitalization) {
		if(marketCapitalization==null | marketCapitalization.equalsIgnoreCase("")){
			this.marketCapitalization = 0;
		}else{
			this.marketCapitalization = Double.parseDouble(marketCapitalization);
		}
	}
	public BigDecimal getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(BigDecimal returnValue) {
		this.returnValue = returnValue;
	}
	
	
	

}

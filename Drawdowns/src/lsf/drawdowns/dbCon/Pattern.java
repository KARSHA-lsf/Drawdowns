package lsf.drawdowns.dbCon;

public class Pattern {
	private float value;
	private int permno;
	private String date;
	
	float getValue() {
		return value;
	}
	void setValue(float value) {
		this.value = value;
	}
	
	int getPermno() {
		return permno;
	}
	void setPermno(int permno) {
		this.permno = permno;
	}
	
	String getDate() {
		return date;
	}
	void setDate(String date) {
		this.date = date;
	}
	
}

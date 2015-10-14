package lsf.drawdowns.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class db_connections {

	String driver = "com.mysql.jdbc.Driver";
	String url="jdbc:mysql://localhost:3306/capm_db";
    String username="root";
    String password="";
    public static Connection con=null;
    PreparedStatement pst=null;
	String jobject;
	
	
	void setCon(){
		try {
            Class.forName(driver);
            con= (Connection)DriverManager.getConnection(url,username,password);
            this.con = con;
        } catch (Exception ex) {
            System.out.println("error m.jdbc.setcon :"+ex);
        }
	}
	public Connection getCon() {
        if (con == null) {
            setCon();
        }
        return con;
    }
	
	public JSONArray select_yeardata(String year) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{

		//Query and it's connections are include under that
		Statement state = getCon().createStatement();
		String query="SELECT permno,date_withyear FROM capm_v2_table WHERE date="+year;
		ResultSet set = state.executeQuery(query);
        
        JSONArray jsonarray=new JSONArray();
        while(set.next()){
        	JSONObject jsonobj=new JSONObject();
        	int permno=set.getInt("permno");
        	String year_date=set.getString("date_withyear");
        	try {
				jsonobj.put("permno",permno);
				jsonobj.put("capm_date",year_date);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	jsonarray.put(jsonobj);
        } 
        System.out.println(jsonarray);
        return jsonarray;
	}
	public JSONArray selectSummaryData(String sqlQuery) throws SQLException{
		JSONArray jsonarray=new JSONArray();
		Statement state = getCon().createStatement();
		ResultSet set = state.executeQuery(sqlQuery);
		while(set.next()){
			JSONObject jsonobj=new JSONObject();
			String year = set.getString("date");
			int count = set.getInt("COUNT(*)");
			try{
				jsonobj.put("year",year);
				jsonobj.put("count",count);
			}catch(JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonobj);
		}
		System.out.println(jsonarray);
		return jsonarray;
	}
}
package lsf.drawdowns.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class db_connections {

	
	String url="jdbc:mysql://localhost:3306/capm_db";
    String username="root";
    String password="";
    Connection con=null;
    PreparedStatement pst=null;
    ResultSet set = null;
	String jobject;
	
	public JSONArray select_yeardata(String year) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		try{
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}catch(Exception e){
				System.out.println(e);
			}
		}catch(Exception e1){
			System.out.println(e1);
		}
		
		//Query and it's connections are include under that
		
		con= (Connection)DriverManager.getConnection(url,username,password);
        String query="SELECT permno,date_withyear FROM capm_v2_table WHERE date="+year;
        pst= (PreparedStatement) con.prepareStatement(query);
        set=pst.executeQuery(query);
        
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
	
	
}
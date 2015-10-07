package lsf.drawdowns.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
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
        String query="SELECT PERMNO,CAPM_resid,date FROM capm_v2_table WHERE date="+year;
        pst= (PreparedStatement) con.prepareStatement(query);
        set=pst.executeQuery(query);
        
        JSONArray jsonarray=new JSONArray();
        int lenght=set.getMetaData().getColumnCount();
        while(set.next()){
        	JSONObject jsonobj=new JSONObject();
        	for(int i=0;i<lenght;i++){
        		try{
        			jsonobj.put(set.getMetaData().getColumnLabel(i+1).toLowerCase(),set.getObject(i+1));
        		}catch(Exception e){
        			System.out.println(e);
        		}
        	}
        	jobject=jsonobj.toString();
        	System.out.println(jobject);	
        	jsonarray.put(jsonobj);
        }
        return jsonarray;
	}
	
	
}

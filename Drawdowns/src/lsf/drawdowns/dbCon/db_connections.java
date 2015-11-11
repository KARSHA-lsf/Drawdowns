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
	String url = "jdbc:mysql://localhost:3306/capm_db";
	String username = "root";
	String password = "";
	
	/*String url = "jdbc:mysql://clipdb-sm3.umiacs.umd.edu:3306/Karsha_drawdowns";
	String username = "karsha";
	String password = "em$.N0w";*/

	public static Connection con = null;
	PreparedStatement pst = null;
	String jobject;

	public void setCon() {
		try {
			Class.forName(driver);
			con = (Connection) DriverManager.getConnection(url, username,
					password);
			this.con = con;
		} catch (Exception ex) {
			System.out.println("error m.jdbc.setcon :" + ex);
		}
	}

	public Connection getCon() {
		if (con == null) {
			setCon();
		}
		return con;
	}

	public ResultSet selectData(String sqlQuery) throws SQLException {
		Statement state = getCon().createStatement();
		ResultSet set = state.executeQuery(sqlQuery);
		return set;
	}
	
	public JSONArray getFromDB(String query, Connection connection) {

		JSONArray jsonArray = new JSONArray();

		try {
			java.sql.Statement statment = connection.createStatement();
			ResultSet resultSet = statment.executeQuery(query);

			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
				for (int i = 0; i < total_rows; i++) {
					try {
						obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
								.toLowerCase(), resultSet.getObject(i + 1));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				jsonArray.put(obj);
			}

			// System.out.println("llll");
			// System.out.println(resultSet.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jsonArray;
	}
}
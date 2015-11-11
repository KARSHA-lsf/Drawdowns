package lsf.drawdowns.dbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class db_connections {

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/capm_db";
	String username = "root";
	String password = "";

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
}
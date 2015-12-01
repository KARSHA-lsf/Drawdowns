package lsf.drawdowns.dbCon;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CRSP_ValueWeightedReturns;
import model.Drawdown;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CLM_Cap_Graph {

	db_connections dbconnection = new db_connections();
	HttpServletRequest request;
	SessionFactory SFact = new Configuration().configure().buildSessionFactory();
	Session session = SFact.openSession();
	org.hibernate.Transaction tx = session.beginTransaction();
	
	
	
	public void request_initalize(HttpServletRequest request_get ) {
		request = request_get;
		//System.out.println(request.getParameter("Q"));
	}

	
	public JsonObject Index_vw_return() {

		ArrayList<CRSP_ValueWeightedReturns> CRSP = new ArrayList<>();
		List<Double> Mkt_Cap = new ArrayList<>();
		List<String> dates = new ArrayList<>();
		String sql = "SELECT * FROM crsp_valueweightedreturns WHERE Crsp_date like '2008%'";
		try {
			ResultSet rs = dbconnection.selectData(sql);

			while (rs.next()) {
				CRSP_ValueWeightedReturns CRSP_obj = new CRSP_ValueWeightedReturns();
				CRSP_obj.setDate(rs.getString("Crsp_date"));
				CRSP_obj.setINDEX(rs.getDouble("Crsp_ret"));
				CRSP_obj.setRET(rs.getDouble("Crsp_value"));
				CRSP.add(CRSP_obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int listsize = CRSP.size();
		for (int i = 0; i < listsize; i++) {
			Mkt_Cap.add(CRSP.get(i).getRET() * CRSP.get(i).getINDEX());
			dates.add(CRSP.get(i).getDate());
		}

		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement returnvalue = gson.toJsonTree(Mkt_Cap);
		JsonElement Rdates = gson.toJsonTree(dates);

		try {
			J_obj.add("ReturnValue", returnvalue);
			J_obj.add("dates", Rdates);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// pwr.print(J_obj);

		return J_obj;

	}

	
	
	public JSONArray dataget_method(){
		String query = "SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date='"
				+ request.getParameter("Q")
				+ request.getParameter("M")
				+ "') AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO='"
				+ request.getParameter("Q")
				+ request.getParameter("M")
				+ "') AS y WHERE x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid";
		SQLQuery q = session.createSQLQuery(query);			
		
		@SuppressWarnings("unchecked")
		
		List<Object[]> results = q.list();
		
		try {
			System.out.println(request.getParameter("M"));

			JSONArray jsonarray = new JSONArray();
			
			for (Object[] aRow : results) {
				JSONObject jsonobj = new JSONObject();
				int permno = (int) aRow[0];
				String year_date = (String) aRow[1];
				if (year_date == null) {

				} 
				else {
					try {
						jsonobj.put("permno", permno);
						jsonobj.put("capm_date", year_date);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					jsonarray.put(jsonobj);
				}					
			}			
			return jsonarray;
			//pwr.print(jsonarray);
		} finally {
			
		}
		
	}
	public JSONArray rangedata_method(){
		
		JSONArray jsonarray = new JSONArray();
		try {
			
			//	String sql ="SELECT * FROM(SELECT v1.*, @counter := @counter +1 AS counter FROM (select @counter:=0) AS initvar, v1) AS X where counter <= (10/100 * @counter) AND YRMO BETWEEN 200401 AND 200412";
				
				String xx = "SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date,@counter := @counter +1 AS counter FROM (select @counter:=0) AS initvar,capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date='"
						+ request.getParameter("Q")
						+ request.getParameter("M")
						+ "') AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO='"
						+ request.getParameter("Q")
						+ request.getParameter("M")
						+ "') AS y WHERE counter <= (10/100 * @counter) AND  x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid";
				ResultSet set = dbconnection.selectData(xx);

				
				while (set.next()) {
					JSONObject jsonobj = new JSONObject();
					int permno = set.getInt("PERMNO");
					String year_date = set.getString("CAPM_resid_D");
					if (year_date == null) {

					} else {
						try {
							jsonobj.put("permno", permno);
							jsonobj.put("capm_date", year_date);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						jsonarray.put(jsonobj);
					}
				}
				//System.out.println();
				System.out.println(jsonarray);
				
				
			} catch (SQLException e) {
				e.printStackTrace(); }
		
			finally {
			// dbconnection.con.close();
		}
		return jsonarray;
	}
	
	public JSONObject indexdata_method(){
		System.out.println("indexData method");
		String sql = "SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"
						+ request.getParameter("Q")
						+ "%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"
						+ request.getParameter("Q")
						+ "%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end";
		
		SQLQuery q = session.createSQLQuery(sql);			
		
		@SuppressWarnings("unchecked")
		
		List<Object[]> results = q.list();
		
		ArrayList<Float> aryValue = new ArrayList<Float>();
		ArrayList<String> aryDate = new ArrayList<String>();
		
		for (Object[] aRow : results) {
			
			String datestring = aRow[0].toString();
			BigDecimal value=new BigDecimal(aRow[1].toString());
			
			aryDate.add(datestring);
			aryValue.add(value.floatValue());
	
		}
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("value", aryValue);
			obj.put("date", aryDate);
		} catch (JSONException e) {
	
			e.printStackTrace();
		}
		
		
		return obj;
		
	}
	public JSONObject summarydata_method(){
		String sql = null;
		if (request.getParameter("D").equals("caff")) {
			sql = "SELECT YEAR(date_withyear) AS date,COUNT(YEAR(date_withyear)) AS count FROM caaf_drawdownend WHERE date_withyear GROUP BY YEAR(date_withyear)";
		} else {
			sql = "SELECT YEAR(CAPM_resid_date) AS date,COUNT(YEAR(CAPM_resid_date)) AS count FROM capm_drawdowns_date WHERE CAPM_resid_date GROUP BY YEAR(CAPM_resid_date)";
		}
	
		

		SQLQuery q = session.createSQLQuery(sql);			
		
		@SuppressWarnings("unchecked")
		
		List<Object[]> results = q.list();
		
		ArrayList<Integer> aryCount = new ArrayList<Integer>();
		ArrayList<Integer> aryYear = new ArrayList<Integer>();

		for (Object[] aRow : results) {
			
			String date=(String) aRow[0].toString();
			String count=(String)aRow[1].toString();
			aryCount.add(Integer.parseInt(count));
			aryYear.add(Integer.parseInt(date));
						
		}
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("year", aryYear);
			obj.put("Total", aryCount);
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return obj;
	}

}

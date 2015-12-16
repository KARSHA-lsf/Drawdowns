package lsf.drawdowns.dbCon;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.CRSP_ValueWeightedReturns;
import org.hibernate.SQLQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CLM_Cap_Graph extends IndexSrvlt {
	
	private static final long serialVersionUID = 1L;

	public void request_initalize(HttpServletRequest request_get ) {
		request = request_get;
		//System.out.println(request.getParameter("Q"));
	}

	
public JsonObject Index_vw_return() {
		
		String sql = "SELECT * FROM crsp_valueweightedreturns WHERE Crsp_date like '" + request.getParameter("Q") + "%'";
		SQLQuery vw = session.createSQLQuery(sql);
		vw.addEntity(CRSP_ValueWeightedReturns.class);
		@SuppressWarnings("unchecked")
		List<CRSP_ValueWeightedReturns> results = vw.list();
		List<Double> Mkt_Cap = new ArrayList<>();
		List<Double> Mkt_Cap_percentage = new ArrayList<>();
		List<String> dates = new ArrayList<>();
					
		int listsize = results.size();
		for (int i = 0; i < listsize; i++) {
			Mkt_Cap.add(results.get(i).getCrsp_ret() * results.get(i).getCrsp_value()* 1000000);
			dates.add(results.get(i).getCrsp_date());
		}
		double min = Mkt_Cap.get(0);
	    double max = min;
	    int length = Mkt_Cap.size();
	    for (int i = 1; i < length; i++) {
	      double value = Mkt_Cap.get(i);
	      min = Math.min(min, value);
	      max = Math.max(max, value);
	    }
	    
		double T_value = max-min;
		
		for (int i = 0; i < listsize; i++) {
			Mkt_Cap_percentage.add(Mkt_Cap.get(i)*100/T_value);
			
		}
		 
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement returnvalue = gson.toJsonTree(Mkt_Cap_percentage);
		JsonElement Rdates = gson.toJsonTree(dates);

		try {
			J_obj.add("ReturnValue", returnvalue);
			J_obj.add("dates", Rdates);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return J_obj;

	}

	
	
	public JSONArray dataget_method(){
		/*String query = "SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date='"
				+ request.getParameter("Q")
				+ request.getParameter("M")
				+ "') AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO='"
				+ request.getParameter("Q")
				+ request.getParameter("M")
				+ "') AS y WHERE x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid";*/
		
		int tmp_month = Integer.valueOf(request.getParameter("M"));
		String month = request.getParameter("M");
		if (tmp_month<10) {
			month = "0"+tmp_month;
		}
		
		
		String yrmo = request.getParameter("Q")+ month;
		String query = "SELECT PERMNO,CAPM_resid_D FROM sys_scatter_plot WHERE YRMO = "+yrmo+" ORDER BY CAPM_resid";
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
	public JSONObject eofMonthLMC(){
		
		String query = "select * from sys_clm_endofmonthlmc where lmcdate like '%"+request.getParameter("Q")+"%'";
		SQLQuery q = session.createSQLQuery(query);			
		
		ArrayList<String> aryDate = new ArrayList<String>();
		ArrayList<Double> aryValue = new ArrayList<Double>();
		
		@SuppressWarnings("unchecked")
		
		List<Object[]> results = q.list();

		for (Object[] aRow : results) {
			
			String date = (String) aRow[0];
			double value= (double) aRow[1];
			aryDate.add(date);
			aryValue.add(value);
			
		}
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("Date",aryDate);
			jsonObject.put("Value",aryValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public JSONObject cumulativeLossMkp() {
		System.out.println("cumulativelossmarketcapitalization");
		String query = "select * from Sys_CLM_CumulativeLMC where date like '%"+request.getParameter("Q")+"%'";
		SQLQuery q = session.createSQLQuery(query);			
	
		ArrayList<String> aryDate = new ArrayList<String>();
		ArrayList<BigDecimal> aryValue = new ArrayList<BigDecimal>();
		
		@SuppressWarnings("unchecked")
		
		List<Object[]> results = q.list();

		for (Object[] aRow : results) {
			
			String date = (String) aRow[0];
			BigDecimal realvalue=((BigDecimal) aRow[1]);
			aryDate.add(date);
			aryValue.add(realvalue);
		}
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("Date",aryDate);
			jsonObject.put("Value",aryValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}

	public JSONObject clmIndexPercentage() {
		String sql = "SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"+request.getParameter("Q")+"%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"+request.getParameter("Q")+"%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end ";	
		ArrayList<String> indexDate = new ArrayList<String>();
		ArrayList<Double> indexValue = new ArrayList<Double>();
		JSONObject obj = new JSONObject();
		double max = -10;
		double tmp = 0;
		double min = 0;
		double tmp_min = 0;
		try {
			ResultSet rset = dbconnection.selectData(sql);
			while(rset.next()){
				tmp = rset.getDouble("Index_values");
				tmp_min = rset.getDouble("Index_values");
				if(tmp>max){
					max=tmp;
				}
				if (tmp_min<min) {
					min=tmp_min;
				}
				indexDate.add(rset.getString("Index_dates"));
				indexValue.add(Double.valueOf(rset.getString("Index_values")));
			}
			for (int j = 0; j < indexValue.size(); j++) {
				indexValue.set(j, indexValue.get(j)*100/(max-min));
			}
			
			obj.put("indexDate", indexDate);
			obj.put("indexValue", indexValue);
		}catch(SQLException | JSONException ex){
			ex.printStackTrace();
		}
		return obj;
		
	}

}

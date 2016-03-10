package lsf.drawdowns.dbCon;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
			Mkt_Cap.add(results.get(i).getCrsp_ret() * results.get(i).getCrsp_value()/100000);
			dates.add(results.get(i).getCrsp_date());
		}
		/*double min = Mkt_Cap.get(0);
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
			
		}*/
		 
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		//JsonElement returnvalue = gson.toJsonTree(Mkt_Cap_percentage);
		//JsonElement Rdates = gson.toJsonTree(dates);
		JsonElement returnvalue = gson.toJsonTree(Mkt_Cap);
		JsonElement Rdates = gson.toJsonTree(dates);
		
		try {
			J_obj.add("ReturnValue", returnvalue);
			J_obj.add("dates", Rdates);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("=D=D   "+J_obj);
		session.flush();
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
			session.flush();
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
		
		session.flush();
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
		session.flush();
		return obj;
	}
	public JSONObject eofMonthLMC(){
		
		String query = "select * from Sys_CLM_EndofMonthLMC where lmcdate like '%"+request.getParameter("Q")+"%'";
		
		if (request.getParameter("T").equals("top10Precent")) {
			query = "select * from Sys_CLM_EndofMonthLMC_top_ten where lmcdate like '%"+request.getParameter("Q")+"%'";
		}else{
			query = "select * from Sys_CLM_EndofMonthLMC where lmcdate like '%"+request.getParameter("Q")+"%'";
		}
		
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
		session.flush();
		return jsonObject;
	}
	
	public JSONObject cumulativeLossMkp() {
		System.out.println("cumulativelossmarketcapitalization");
		String query; 
		if(request.getParameter("T").equals("top10Precent")){
			query = "select all_dates,cumilativeLossMcap from sys_blu_cumilative_2004to2014top10 where all_dates like '%"+request.getParameter("Q")+"%'";
				
		}
		else if(request.getParameter("T").equals("month")){
			//query = "select * from sys_clm_cumulativelmc where date like '%"+request.getParameter("Q")+"%'";
			query="select all_dates,cumilativeLossMcap from sys_blu_cumilative_2004to2014all where all_dates like '%"+request.getParameter("Q")+"%'";
		}
		else{
			//query="select * from sys_clm_cumulativelmc where date like '%"+request.getParameter("Q")+"%'";
			query="select all_dates,cumilativeLossMcap from sys_blu_cumilative_2004to2014all where all_dates like '%"+request.getParameter("Q")+"%'";
		}
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
		session.flush();
		return jsonObject;
		
	}

	public JSONObject clmIndexPercentage() {
		String sql = "SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"+request.getParameter("Q")+"%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"+request.getParameter("Q")+"%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end ";	
		ArrayList<String> indexDate = new ArrayList<String>();
		ArrayList<Double> indexValue = new ArrayList<Double>();
		JSONObject obj = new JSONObject();
		double min = 10;
		double max = -10;
		double tmp = 0;
		double devisor = 0;
		try {
			ResultSet rset = dbconnection.selectData(sql);
			while(rset.next()){
				tmp = rset.getDouble("Index_values");
				if(tmp<min){
					min=tmp;
				}
				if(tmp>max){
					max=tmp;
				}
				indexDate.add(rset.getString("Index_dates"));
				indexValue.add(Double.valueOf(rset.getString("Index_values")));
			}
			devisor = min-max;
			if (devisor<0) {
				devisor=-1*devisor;
			} 
			for (int j = 0; j < indexValue.size(); j++) {
				indexValue.set(j, indexValue.get(j)*100/(devisor));
			}
			
			obj.put("indexDate", indexDate);
			obj.put("indexValue", indexValue);
		}catch(SQLException | JSONException ex){
			ex.printStackTrace();
		}
	
		return obj;
		
	}
	public JsonObject Perm_History_Method(){
		String sql = "SELECT CAPM_resid_D,CAPM_resid FROM sys_scatter_plot  where PERMNO =" + request.getParameter("P") +" AND YRMO LIKE '" + request.getParameter("Q") + "%'";
		SQLQuery q = session.createSQLQuery(sql);
		
		List<String> Lidate = new ArrayList<>();
		List<BigDecimal> Livalue = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> results = q.list();
		//System.out.println(results.get(0).toString());
			
		for (Object[] perhis : results){
			String date = (String) perhis[0]; 
			BigDecimal value = (BigDecimal) perhis[1];
			Lidate.add(date);
			Livalue.add(value);
		}
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement phvalue = gson.toJsonTree(Livalue);
		JsonElement phdates = gson.toJsonTree(Lidate);


		try {
			J_obj.add("Drawdown_value", phvalue);
			J_obj.add("Drawdown_date", phdates);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		session.flush();
		return J_obj;
	}
	public JsonObject perm_return_method(){
		String sql = "SELECT yrmo,value1 FROM caaf_returns  where PERMNO =" + request.getParameter("P") +" AND YRMO LIKE '" + request.getParameter("Q") + "%'";
		SQLQuery q = session.createSQLQuery(sql);
		
		List<Integer> Arr_yrmo = new ArrayList<>();
		List<BigDecimal> Arr_value = new ArrayList<>();
		List<String> End_date = new ArrayList<>(); 
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = q.list();
		for (Object[] returns : result) {
			int yrmo = (int) returns[0];
			BigDecimal value = (BigDecimal) returns[1];
			Arr_yrmo.add(yrmo);
			Arr_value.add(value);
		}
		for (int i = 0; i < result.size(); i++) {
			int year = Arr_yrmo.get(i)/100;
			int month = Arr_yrmo.get(i)%100;
			String date = getDate(month, year);
			End_date.add(date);
		}
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement retvalue = gson.toJsonTree(Arr_value); 
		JsonElement enddate = gson.toJsonTree(End_date);
		
		J_obj.add("Return_value",retvalue);
		J_obj.add("End_date", enddate); 
		session.flush();
		return J_obj;
		
	}
	public String getDate(int month, int year) {
	    Calendar calendar = Calendar.getInstance();
	    // passing month-1 because 0-->jan, 1-->feb... 11-->dec
	    calendar.set(year, month - 1, 1);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    Date date = calendar.getTime();
	    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	    return DATE_FORMAT.format(date);
	}
	public JSONObject pattern() throws JSONException{
		String sql = "select yrmo,blue,red,green,X,Y,Z from pattern";
		SQLQuery q = session.createSQLQuery(sql);

		JSONArray jsonarray = new JSONArray();
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = q.list();
		for (Object[] returns : result) {
			JSONObject jsonobj = new JSONObject();
			
			int date=(int) returns[0];	
			BigDecimal blue = (BigDecimal) returns[1];
			BigDecimal red = (BigDecimal) returns[2];
			BigDecimal green = (BigDecimal) returns[3];
			String	X = (String)returns[4];
			String	Y = (String)returns[5];
			String	Z = (String)returns[6];
			
			jsonobj.put("yrmo", date);
			jsonobj.put("blue", blue);
			jsonobj.put("red", red);
			jsonobj.put("green", green);
			jsonobj.put("X", X);
			jsonobj.put("Y", Y);
			jsonobj.put("Z", Z);
			
			jsonarray.put(jsonobj);
			
		}
		JSONObject jo = new JSONObject();
		jo.put("person", jsonarray);
		System.out.println(jsonarray);
		return jo;
	}
	
	public JsonObject scatterMcap(){
		String sql = "SELECT CAPM_resid_date,marketCapitalization from sys_top10_losess WHERE YRMO like '" + request.getParameter("yrmo") + "%'" + "ORDER BY marketCapitalization";
		SQLQuery q = session.createSQLQuery(sql);
		
		List<BigDecimal> Arr_mcap = new ArrayList<>();
		List<String> Arr_date = new ArrayList<>();
		
		List<Object[]> result = q.list();
		for (Object[] returns : result) {
			//System.out.println(returns[1]);
			String date = (String) returns[0];
			BigDecimal mcap = (BigDecimal) returns[1];
			
			Arr_mcap.add(mcap);
			Arr_date.add(date);
									
		}
		
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement date = gson.toJsonTree(Arr_date);
		JsonElement mcap = gson.toJsonTree(Arr_mcap);
		
		
		J_obj.add("mcap", mcap);
		J_obj.add("date", date);
		
		session.flush();
		return J_obj;
			
		
	}
	
	public JsonObject monthlymcap(){
		String month = request.getParameter("month");
		int month2 = Integer.parseInt(month);
		String sql = null;
		if(month2 < 10){
			
		 sql = "SELECT NAICS,sum(red) FROM red_individual_level where month =" + request.getParameter("year") + "0"+ request.getParameter("month") +" group by NAICS";
		
		}else{
			 sql = "SELECT NAICS,sum(red) FROM red_individual_level where month =" + request.getParameter("year") +  request.getParameter("month") +" group by NAICS";
		}
		SQLQuery q = session.createSQLQuery(sql);
		
		List<BigDecimal> Arr_mcap = new ArrayList<>();
		List<Integer> Arr_naics = new ArrayList<>(); 
		
		List<Object[]> result = q.list();
		
		for (Object[] returns : result) {
			int naics =  (int) returns[0];
			BigDecimal mcap = (BigDecimal) returns[1];
			//System.out.println(mcap);
			Arr_mcap.add(mcap);
			Arr_naics.add(naics);
			
		}
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement naics = gson.toJsonTree(Arr_naics);
		JsonElement mcap = gson.toJsonTree(Arr_mcap);
		
		J_obj.add("naics", naics);
		J_obj.add("mcap", mcap);
		
		session.flush();
		return J_obj;
		
		
	}
	
	public JsonObject individual_Equity(String Mcap,String date) {
		
		String sql = "SELECT b.*,a.NAICS,a.Naics_code,a.Naics_name,a.COMNAM,a.TSYMBOL FROM (SELECT permno,LOSSMcap FROM sys_top10_losess WHERE CAPM_resid_date = '"+date+"' and marketCapitalization = "+Mcap+")as b JOIN perm_details as a ON a.PERMNO = b.permno;";
		//String sql = "SELECT permno,LOSSMcap FROM sys_top10_losess  WHERE CAPM_resid_date = '"+date+"' and marketCapitalization = "+Mcap+";";
		SQLQuery q = session.createSQLQuery(sql);
		List<Object[]> result = q.list();
		int permno = 0;
		Double LossMcap = null;
		int Naics = 0;
		int Naics_code = 0;
		String Naics_name =null;
		String Comnam = null;
		String Tsymbol = null;
		
		for (Object[] returns : result) {
			 permno =  (int) returns[0];
			 LossMcap = (Double) returns[1];
			 Naics = (int)returns[2];
			 Naics_code = (int)returns[3];
			 Naics_name = (String)returns[4];
			 Comnam = (String)returns[5];
			 Tsymbol = (String)returns[6];
			 
			 
		}
		
		Gson gson = new Gson();
		JsonObject J_obj = new JsonObject();
		JsonElement JE_permno = gson.toJsonTree(permno);
		JsonElement JE_naics = gson.toJsonTree(Naics);
		JsonElement JE_LossMcap = gson.toJsonTree(LossMcap);
		JsonElement JE_Naics_code = gson.toJsonTree(Naics_code);
		JsonElement JE_Naics_name = gson.toJsonTree(Naics_name);
		JsonElement JE_Comnam = gson.toJsonTree(Comnam);
		JsonElement JE_Tsymbol = gson.toJsonTree(Tsymbol);
		J_obj.add("Permno",JE_permno);
		J_obj.add("Naics",JE_naics);
		J_obj.add("LossMcap",JE_LossMcap);
		J_obj.add("Naics_code",JE_Naics_code);
		J_obj.add("Naics_name",JE_Naics_name);
		J_obj.add("Comnam",JE_Comnam);
		J_obj.add("Tsymbol",JE_Tsymbol);
		
		return J_obj;
	}

}
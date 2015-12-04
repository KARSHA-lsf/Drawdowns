package lsf.drawdowns.dbCon;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;

/**
 * Servlet implementation class IndexSrvlt
 */
@WebServlet(description = "this servelet will be the startup servlet and it "
		+ "may allow to open DB connection", urlPatterns = { "/IndexSrvlt" })
public class IndexSrvlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexSrvlt() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Gson gson = new Gson();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Drawdown system on live...!!!!!!1");
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Doget method running now........");

		String userPath = request.getServletPath();
		db_connections dbconnection = new db_connections();

		PrintWriter pwr = response.getWriter();
		
		PrintWriter pwr = response.getWriter();
		String yrmo = request.getParameter("Q")+request.getParameter("M");

		if (userPath.equals("/dataGet")) {

			String query = "SELECT PERMNO,CAPM_resid_D FROM sys_scatter_plot WHERE YRMO = :yrmo ORDER BY CAPM_resid";
			SQLQuery q = (SQLQuery) session.createSQLQuery(query).setParameter("yrmo", yrmo);			
			
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
				
				pwr.print(jsonarray);
			} finally {
				
			}
		} else if (userPath.equals("/rangeData")) {

			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.rangedata_method());

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
					System.out.println();
					System.out.println(jsonarray);
					
					pwr.print(jsonarray);
				} catch (SQLException e) {
					e.printStackTrace(); }
				finally {
				// dbconnection.con.close();
			}

		} else if (userPath.equals("/summaryData")) {
			String sql = null;
			if (request.getParameter("D").equals("caff")) {
				sql = "SELECT YEAR(date_withyear) AS date,COUNT(YEAR(date_withyear)) AS count FROM caaf_drawdownend WHERE date_withyear GROUP BY YEAR(date_withyear)";
			} else {
				sql = "SELECT YEAR(CAPM_resid_date) AS date,COUNT(YEAR(CAPM_resid_date)) AS count FROM capm_drawdowns_date WHERE CAPM_resid_date GROUP BY YEAR(CAPM_resid_date)";
			}
		
		} else if (userPath.equals("/indexData")) {
			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.indexdata_method());
		}

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
				pwr.print(obj);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		} else if (userPath.equals("/indexData")) {
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
			
			
			pwr.print(obj);
			
		} 
		
		 else if (userPath.equals("/test_getSet")) {
			
				
				
				
				String all_drawdown = "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO LIKE '"+request.getParameter("Q")+"%' AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo LIKE '"+request.getParameter("Q")+"%') AS B ON A.PERMNO = B.permno ) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date LIKE '"+request.getParameter("Q")+"%' AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo LIKE '"+request.getParameter("Q")+"%') AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
				//String all_drawdown ="SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 200512 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 200512) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 200512 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 200512) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";

			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);

			JsonObject J_obj = new JsonObject();
			// JsonObject index_vw = clm_grp.Index_vw_return();
			JsonObject index_vw = clm_grp.Index_vw_return();
			JSONObject cum_loss = clm_grp.cumulativeLossMkp();
			JSONObject Index_percent = clm_grp.clmIndexPercentage();
			JSONObject eofobj = clm_grp.eofMonthLMC();

			JsonParser jsonParser = new JsonParser();
			JsonObject eof = (JsonObject) jsonParser.parse(eofobj.toString());
			JsonObject cum = (JsonObject) jsonParser.parse(cum_loss.toString());
			JsonObject Ipercent = (JsonObject) jsonParser.parse(Index_percent
					.toString());
			J_obj.add("Return_Value", index_vw.getAsJsonArray("ReturnValue"));
			J_obj.add("Return_Dates", index_vw.getAsJsonArray("dates"));
			J_obj.add("Value", cum.getAsJsonArray("Value"));
			J_obj.add("Date", cum.getAsJsonArray("Date"));
			J_obj.add("Index_Value", Ipercent.getAsJsonArray("indexValue"));
			J_obj.add("Index_Date", Ipercent.getAsJsonArray("indexDate"));
			J_obj.add("eof_Value", eof.getAsJsonArray("Value"));
			J_obj.add("eof_Date", eof.getAsJsonArray("Date"));
			System.out.println(J_obj);
			pwr.print(J_obj);
			System.out.print(J_obj);
		}
		tx.commit();
		session.close();
		
	
				// CAPM VW Return calculation
			
				ArrayList<CRSP_ValueWeightedReturns> CRSP = new ArrayList<>();
				List<Double> Mkt_Cap = new ArrayList<>();
				List<String> dates = new ArrayList<>();
				String sql = "SELECT * FROM `CRSP_ValueWeightedReturns` WHERE Crsp_date like '2008%'";
				try {
					ResultSet rs = dbconnection.selectData(sql);
					
					while(rs.next()){
						CRSP_ValueWeightedReturns CRSP_obj = new CRSP_ValueWeightedReturns();
						CRSP_obj.setDate(rs.getString("Crsp_date"));
						CRSP_obj.setINDEX(rs.getDouble("Crsp_value"));
						CRSP_obj.setRET(rs.getDouble("Crsp_ret"));
						CRSP.add(CRSP_obj);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int listsize = CRSP.size();
				for(int i=0;i < listsize;i++)
				{
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
				
				
					//pwr.print(J_obj);
				
						
		
			
	
			

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
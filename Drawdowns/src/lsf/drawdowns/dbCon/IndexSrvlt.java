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

		SessionFactory SFact = new Configuration().configure().buildSessionFactory();
		Session session = SFact.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		
		
		if (userPath.equals("/dataGet")) {
			 PrintWriter pwr = response.getWriter();
				CLM_Cap_Graph clm_grp  = new CLM_Cap_Graph();
				clm_grp.request_initalize(request);
				pwr.print(clm_grp.dataget_method());

			
		} else if (userPath.equals("/rangeData")) {

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

					JSONArray jsonarray = new JSONArray();
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
					System.out.println();
					System.out.println(jsonarray);
					PrintWriter pwr = response.getWriter();
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
			
			PrintWriter pwr = response.getWriter();
			pwr.print(obj);
			
		} 
		
		 else if (userPath.equals("/test_getSet")) {
			 PrintWriter pwr = response.getWriter();
				CLM_Cap_Graph clm_grp  = new CLM_Cap_Graph();
				clm_grp.request_initalize(request);
				pwr.print(clm_grp.CLM_cap());
		 }
				
				
				
				
		
		}
	

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
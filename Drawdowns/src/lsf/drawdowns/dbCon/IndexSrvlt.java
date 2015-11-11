package lsf.drawdowns.dbCon;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

		Pattern p=new Pattern();
		System.out.println("Doget method running now........");

		String userPath = request.getServletPath();
		db_connections dbconnection = new db_connections();

		if (userPath.equals("/dataGet")) {
			
			try {
								
				System.out.println(request.getParameter("M"));

				String query = "SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D,y.CAPM_resid AS value FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date='"+ request.getParameter("Q") + request.getParameter("M") + "') AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO='"+ request.getParameter("Q") + request.getParameter("M") + "') AS y WHERE x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid";
				ResultSet set = dbconnection.selectData(query);
								
				JSONArray jsonarray = new JSONArray();
				
				while (set.next()) {
					
					JSONObject jsonobj = new JSONObject();
					int permno = set.getInt("PERMNO");
					String year_date = set.getString("CAPM_resid_D");
										
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
				PrintWriter pwr = response.getWriter();
				pwr.print(jsonarray);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		else if (userPath.equals("/summaryData")) {
			try {
				String sql = null;
				if (request.getParameter("D").equals("caff")) {
					sql = "SELECT YEAR(date_withyear) AS date,COUNT(YEAR(date_withyear)) AS count FROM caaf_drawdownend GROUP BY YEAR(date_withyear)";
				} else {
					sql = "SELECT YEAR(CAPM_resid_date) AS date,COUNT(YEAR(CAPM_resid_date)) AS count FROM capm_drawdowns_date GROUP BY YEAR(CAPM_resid_date)";
				}
				ResultSet set = dbconnection.selectData(sql);

				ArrayList<Integer> aryCount = new ArrayList<Integer>();
				ArrayList<Integer> aryYear = new ArrayList<Integer>();

				while (set.next()) {
					if (set.getInt("date") == 0) {

					} else {
						aryCount.add(set.getInt("count"));
						aryYear.add(set.getInt("date"));
					}
				}
				JSONObject obj = new JSONObject();
				obj.put("Total", aryCount);
				obj.put("year", aryYear);
				PrintWriter pwr = response.getWriter();
				pwr.print(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else if (userPath.equals("/indexData")) {
			System.out.println("indexData method");
			try {
				
				
				
				ResultSet set = dbconnection.selectData("SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"+ request.getParameter("Q") + "%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"+ request.getParameter("Q") + "%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end");

				ArrayList<Float> aryValue = new ArrayList<Float>();
				ArrayList<String> aryDate = new ArrayList<String>();
				while (set.next()) {
					aryValue.add(set.getFloat("Index_values"));
					aryDate.add(set.getString("Index_dates"));
				}
				JSONObject obj = new JSONObject();
				obj.put("value", aryValue);
				obj.put("date", aryDate);
				PrintWriter pwr = response.getWriter();
				pwr.print(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} else if (userPath.equals("/Pattern")) {
			System.out.println("Pattern running");
			/*
			try {

				ResultSet set = dbconnection
						.selectData("select one_d from v_index_drawdown_dates where one_d like '%"
								+ request.getParameter("Q") + "%'");
				JSONArray jsonarray = new JSONArray();
				while (set.next()) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("value", set.getString("one_d"));
					jsonarray.put(set.getString("one_d"));
				}
				PrintWriter pwr = response.getWriter();
				pwr.print(jsonarray);
				System.out.println(jsonarray);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		db_connections dbConnection=new db_connections();
		Pattern patObj=new Pattern();
		if(request.getServletPath().equals("/IndexSrvlt")){
			
			System.out.println("post methods");
			
			String query = "SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D,y.CAPM_resid AS value FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date='"+ request.getParameter("Q") + request.getParameter("M") + "') AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO='"+ request.getParameter("Q") + request.getParameter("M") + "') AS y WHERE x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid";
			try {
				ResultSet resultset=dbConnection.selectData(query);
				while(resultset.next()){
					patObj.setValue(resultset.getFloat("value"));		
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
}

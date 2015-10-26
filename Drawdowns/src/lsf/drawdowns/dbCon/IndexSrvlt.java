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
   //Gson gson = new Gson();
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Doget method running now........");
		
		String userPath = request.getServletPath();
		db_connections dbconnection=new db_connections();
		
		if (userPath.equals("/dataGet")) {
			try {
				ResultSet set = dbconnection.selectData("SELECT permno,date_withyear FROM capm_v2_table WHERE date='"+request.getParameter("Q")+"'");
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
				PrintWriter pwr=response.getWriter();
				pwr.print(jsonarray);		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}  else if(userPath.equals("/summaryData")){
			try {	
				String sql = null;
				if(request.getParameter("D").equals("caff")){
					sql = "select date,COUNT(date) from caff_drawdowns group by date";
				}else{
					sql = "select date,COUNT(date) from capm_v2_table group by date";
				}
				ResultSet set =  dbconnection.selectData(sql);
				
				ArrayList<Integer> aryCount = new ArrayList<Integer>();
				ArrayList<Integer> aryYear = new ArrayList<Integer>();
				
				while(set.next()){
					aryCount.add(set.getInt("COUNT(date)"));
					aryYear.add(set.getInt("date"));
				}
				JSONObject obj = new JSONObject();
				obj.put("Total", aryCount);
				obj.put("year", aryYear);
				PrintWriter pwr=response.getWriter();
				pwr.print(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(userPath.equals("/indexData")){
			System.out.println("indexData method");
			try {
				ResultSet set = dbconnection.selectData("select Index_dates,Index_values from indexDrawdown where Year='"+request.getParameter("Q")+"'");
				ArrayList<Float> aryValue = new ArrayList<Float>();
				ArrayList<String> aryDate = new ArrayList<String>();
				while(set.next()){
					aryValue.add(set.getFloat("Index_values"));
					aryDate.add(set.getString("Index_dates"));
				}
				JSONObject obj = new JSONObject();
				obj.put("value", aryValue);
				obj.put("date", aryDate);
				PrintWriter pwr=response.getWriter();
				pwr.print(obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(userPath.equals("/index")){
			try {
				ResultSet set = dbconnection.selectData("select Index_dates from indexDrawdown where Year='"+request.getParameter("Q")+"'");
				JSONArray jsonarray=new JSONArray();
				while(set.next()){
					JSONObject jsonobj=new JSONObject();
					jsonobj.put("value", set.getString("Index_dates"));
					jsonarray.put(jsonobj);
				}
				PrintWriter pwr=response.getWriter();
				pwr.print(jsonarray);
				System.out.println(jsonarray);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}

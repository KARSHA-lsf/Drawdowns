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




import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
   Gson gson = new Gson();
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
				PrintWriter pwr=response.getWriter();
				pwr.print(dbconnection.select_yeardata(request.getParameter("Q")));		
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		} else if(userPath.equals("/summaryData")){
			System.out.println("summaryData method");
			try {	
				ResultSet set = dbconnection.selectData("select date,COUNT(*) from capm_v2_table group by date");
				ArrayList<Integer> year =  new ArrayList<Integer>();
				ArrayList<Integer> count =  new ArrayList<Integer>();
				while(set.next()){
					year.add(set.getInt("date"));
					count.add(set.getInt("COUNT(*)"));
				}
				JSONObject obj = new JSONObject();
				JsonElement links = gson.toJsonTree(year);
				JsonElement nodes = gson.toJsonTree(count);
				obj.put("count", nodes);
				obj.put("year", links);
				System.out.println(obj);
				PrintWriter pwr=response.getWriter();
				pwr.print(obj);		
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

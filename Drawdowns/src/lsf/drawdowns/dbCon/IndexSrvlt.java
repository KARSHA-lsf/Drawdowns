package lsf.drawdowns.dbCon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;

/**
 * Servlet implementation class IndexSrvlt
 */
@WebServlet(description = "this servelet will be the startup servlet and it "
		+ "may allow to open DB connection", urlPatterns = { "/IndexSrvlt" })


public class IndexSrvlt extends HttpServlet {
	
	db_connections dbconnection = new db_connections();
	HttpServletRequest request;
	SessionFactory SFact = new Configuration().configure().buildSessionFactory();
	Session session = SFact.openSession();
	org.hibernate.Transaction tx = session.beginTransaction();
	
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
		CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
		clm_grp.request_initalize(request);
		String userPath = request.getServletPath();

		PrintWriter pwr = response.getWriter();

		if (userPath.equals("/dataGet")) {
			pwr.print(clm_grp.dataget_method());

		} else if (userPath.equals("/rangeData")) {
			pwr.print(clm_grp.rangedata_method());

		} else if (userPath.equals("/summaryData")) {
			pwr.print(clm_grp.summarydata_method());
			
		} else if (userPath.equals("/indexData")) {
			pwr.print(clm_grp.indexdata_method());
			
		}
		else if (userPath.equals("/test_getSet")) {
			clm_grp.request_initalize(request);

			JsonObject J_obj = new JsonObject();
			// JsonObject index_vw = clm_grp.Index_vw_return();
			JsonObject index_vw = clm_grp.Index_vw_return();
			JSONObject cum_loss = clm_grp.cumulativeLossMkp();
			//JSONObject Index_percent = clm_grp.clmIndexPercentage();
			JSONObject Index_percent = clm_grp.indexdata_method();
			JSONObject eofobj = clm_grp.eofMonthLMC();

			JsonParser jsonParser = new JsonParser();
			JsonObject eof = (JsonObject) jsonParser.parse(eofobj.toString());
			JsonObject cum = (JsonObject) jsonParser.parse(cum_loss.toString());
			JsonObject Ipercent = (JsonObject) jsonParser.parse(Index_percent.toString());
			
			J_obj.add("Index_VW_Return", index_vw.getAsJsonArray("ReturnValue"));
			J_obj.add("Return_Dates", index_vw.getAsJsonArray("dates"));
			J_obj.add("Cumulative_Loss_Market_capitalization", cum.getAsJsonArray("Value"));
			J_obj.add("Date", cum.getAsJsonArray("Date"));
			J_obj.add("Index_Drawdown", Ipercent.getAsJsonArray("value"));
			J_obj.add("Index_Date", Ipercent.getAsJsonArray("date"));
			J_obj.add("EndofMonth_Total_Loss_Market_capitalization", eof.getAsJsonArray("Value"));
			J_obj.add("eof_Date", eof.getAsJsonArray("Date"));
			//System.out.println(J_obj);
			pwr.print(J_obj);
			System.out.print(J_obj);
			
		}else if(userPath.equals("/perm_history")){
			
			//pwr.print(clm_grp.Perm_History_Method());
			//pwr.print(clm_grp.perm_return_method());
			
			JsonObject dialog = new JsonObject();
			JsonObject ret = clm_grp.perm_return_method();
			JsonObject draw = clm_grp.Perm_History_Method();
			dialog.add("Drawdown_value", draw.getAsJsonArray("Drawdown_value"));
			dialog.add("Drawdown_date", draw.getAsJsonArray("Drawdown_date"));
			dialog.add("Return_value", ret.getAsJsonArray("Return_value"));
			dialog.add("End_date", ret.getAsJsonArray("End_date"));
			pwr.print(dialog); 
		}
		else if(userPath.equals("/pattern")){
			try {

				pwr.print(clm_grp.pattern());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}

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
			System.out.println(clm_grp.indexdata_method());
			pwr.print(clm_grp.indexdata_method());
			
		}
		else if (userPath.equals("/test_getSet")) {
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

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
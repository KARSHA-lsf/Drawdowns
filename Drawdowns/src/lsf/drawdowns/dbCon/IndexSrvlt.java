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

		PrintWriter pwr = response.getWriter();

		if (userPath.equals("/dataGet")) {

			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.dataget_method());

		} else if (userPath.equals("/rangeData")) {

			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.rangedata_method());

		} else if (userPath.equals("/summaryData")) {
			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.summarydata_method());

		} else if (userPath.equals("/indexData")) {
			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.indexdata_method());
		}

		else if (userPath.equals("/test_getSet")) {

			CLM_Cap_Graph clm_grp = new CLM_Cap_Graph();
			clm_grp.request_initalize(request);
			pwr.print(clm_grp.clmIndexPercentage());
			
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}
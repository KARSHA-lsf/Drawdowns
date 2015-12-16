package lsf.drawdowns.dbCon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class annualy_analysis
 */
@WebServlet(description = "visualize the annual up to 20% drawdwons under"+
		" the LossMcap and Drawdowns", urlPatterns = { "/annualy_analysis" })
public class annualy_analysis extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public annualy_analysis() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Running on : Annualy Analysis servelet ");

		String userPath = request.getServletPath();
		System.out.println(userPath);
		SessionFactory SFact = new Configuration().configure()
				.buildSessionFactory();
		Session session = SFact.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		PrintWriter pwr = response.getWriter();

		String yrmo = request.getParameter("yrmo");
		String Dr_top = request.getParameter("Dr_top");
		String LossMcap_top = request.getParameter("LossMcap_top");
		
		if (userPath.equals("/GetAnnualData")) {
			
			String query = "SELECT PERMNO,CAPM_resid_D FROM sys_scatter_plot WHERE YRMO LIKE '"+yrmo+"%' ORDER BY CAPM_resid";
			SQLQuery q = (SQLQuery) session.createSQLQuery(query);

			@SuppressWarnings("unchecked")
			List<Object[]> results = q.list();

			try {

				JSONArray jsonarray = new JSONArray();

				for (Object[] aRow : results) {
					JSONObject jsonobj = new JSONObject();
					int permno = (int) aRow[0];
					String year_date = (String) aRow[1];
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

				pwr.print(jsonarray);
			} catch (Exception e) {
				System.out
						.println("some error occured during scatterPlot data pre processing");
			}
			
		}else if (userPath.equals("/TopLossesAnnualData")) {
			String query = "SELECT PERMNO,CAPM_resid_date as CAPM_resid_D FROM sys_top10_losess WHERE YRMO LIKE '"+yrmo+"%' ORDER BY CAPM_resid";
			SQLQuery q = (SQLQuery) session.createSQLQuery(query);

			@SuppressWarnings("unchecked")
			List<Object[]> results = q.list();

			try {

				JSONArray jsonarray = new JSONArray();

				for (Object[] aRow : results) {
					JSONObject jsonobj = new JSONObject();
					int permno = (int) aRow[0];
					String year_date = (String) aRow[1];
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

				pwr.print(jsonarray);
			} catch (Exception e) {
				System.out
						.println("some error occured during scatterPlot data pre processing");
			}
		}else if(userPath.equals("/blueBar_calcu")){
			String query = "SELECT PERMNO,CAPM_resid_date as CAPM_resid_D FROM sys_top10_losess WHERE YRMO LIKE '2004%' ORDER BY CAPM_resid";
			SQLQuery q = (SQLQuery) session.createSQLQuery(query);

			@SuppressWarnings("unchecked")
			List<Object[]> results = q.list();
			try {
				JSONArray jsonarray = new JSONArray();
				for (Object[] aRow : results) {
					JSONObject jsonobj = new JSONObject();
					int permno = (int) aRow[0];
					String year_date = (String) aRow[1];
					if (year_date == null) {
						System.out.println("null date is not valid..");
						continue;
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

				pwr.print(jsonarray);
			} catch (Exception e) {
				System.out
						.println("some error occured during scatterPlot data pre processing");
			}
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

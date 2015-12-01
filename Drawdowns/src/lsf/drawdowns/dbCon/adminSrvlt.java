package lsf.drawdowns.dbCon;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.json.JSONException;
import org.json.JSONObject;

import model.Drawdown;
import model.Sys_CLM_EndofMonthLMC;

/**
 * Servlet implementation class adminSrvlt
 */
@WebServlet("/adminSrvlt")
public class adminSrvlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public adminSrvlt() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter pwr = response.getWriter();
		db_connections dbconnection = new db_connections();

		SessionFactory SFact = new Configuration().configure()
				.buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();

		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		/**  end of the month loss market capitalization **/
		Sys_CLM_EndofMonthLMC obj = new Sys_CLM_EndofMonthLMC();
		try {
			
			ResultSet rset = dbconnection
					.selectData("SELECT DISTINCT CAPM_resid_date,YRMO_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ORDER BY CAPM_resid_date");
			
			ArrayList<String> eofdate = getEndOfMonthDates(rset);	
				
			String all_drawdown =  "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 201412 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 201412) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 201412) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
			List<Drawdown> result=q.list();	
			
			ArrayList<Double> eofvalue = getEndOfMonthvalues(result,eofdate);
			
			for (int i = 0; i < eofdate.size(); i++) {
				obj.setLmcdate(eofdate.get(i));
				obj.setValue(eofvalue.get(i));				
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**  end of the month loss market capitalization **/
		session.save(obj);
		session.getTransaction().commit();

	}


	private ArrayList<Double> getEndOfMonthvalues(List<Drawdown> result,
			ArrayList<String> eofdate) {
		double empValue[] = new double[1000];
		ArrayList<Double> eofvalue = new ArrayList<Double>();
		for (int j = 0; j < eofdate.size(); j++) {		
			double x= 0;
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Drawdown drawdown = (Drawdown) iterator.next();
				double returnvalue = (drawdown.getReturnValue().doubleValue()-1)*drawdown.getMarketCapitalization();
				if (drawdown.getDrawdownDate().equals(eofdate.get(j))) {
					x = x + returnvalue;
				} 
			}
			empValue[j]= x;
		}
		
		for (int j = 0; j < empValue.length; j++) {
			if (empValue[j]!=0) {
				eofvalue.add(empValue[j]);
			}		
		}
		// TODO Auto-generated method stub
		return eofvalue;
	}

	private ArrayList<String> getEndOfMonthDates(ResultSet rset) {
		ArrayList<String> edate = new ArrayList<>();
		String[] eofdate = new String[1000];

		int j=0;
		int y = 200401;		
		try {
			while (rset.next()) {
				if (!rset.getString("CAPM_resid_date").equals("")) {
					String d1 = rset.getString("CAPM_resid_date");
					if (rset.getInt("YRMO_date")==y) {
						eofdate[j]=d1;						
					} else {
						if ((j+1)%12==0) {
							y=y+89;
							j++;
						}else{
							y=y+1;
							j=j+1;
						}				
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < eofdate.length; i++) {
			if (eofdate[i]!=null) {
				edate.add(eofdate[i]);
				
			}	
		}
		return edate;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	

	private double[] getEndOfMonthLossMC(List<Drawdown> result, String[] empDate) {
		double empValue[] = new double[12];
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			Drawdown drawdown = (Drawdown) iterator.next();
			String check = drawdown.getDrawdownDate();
			double returnvalue = drawdown.getReturnValue().doubleValue() - 1;
			if (drawdown.getDrawdownDate().equals(empDate[0])) {
				empValue[0] = empValue[0]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[1])) {
				empValue[1] = empValue[1]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[2])) {
				empValue[2] = empValue[2]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[3])) {
				empValue[3] = empValue[3]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[4])) {
				empValue[4] = empValue[4]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[5])) {
				empValue[5] = empValue[5]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[6])) {
				empValue[6] = empValue[6]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[7])) {
				empValue[7] = empValue[7]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[8])) {
				empValue[8] = empValue[8]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[9])) {
				empValue[9] = empValue[9]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[10])) {
				empValue[10] = empValue[10]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			} else if (drawdown.getDrawdownDate().equals(empDate[11])) {
				empValue[11] = empValue[11]
						+ (drawdown.getMarketCapitalization() * returnvalue);
			}
		}
		return empValue;
	}

}

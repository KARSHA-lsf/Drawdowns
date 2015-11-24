package lsf.drawdowns.dbCon;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter pwr = response.getWriter();
		JSONObject obj = new JSONObject();
		db_connections dbconnection = new db_connections();

		SessionFactory SFact = new Configuration().configure()
				.buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// String all_drawdown =
		// "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 201512 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 201512) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201512 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 201512) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
		// SQLQuery q = session.createSQLQuery(all_drawdown);
		// q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
		// List<Drawdown> result=q.list();

		try {
			ResultSet rset = dbconnection
					.selectData("SELECT DISTINCT CAPM_resid_date,YRMO_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ORDER BY CAPM_resid_date");
			Date ordate = df.parse("2004-01-01");
			ArrayList<String> edate = getEndOfMonthDates(rset);	
			obj.put("d", edate);
			pwr.print(obj);
			
			String all_drawdown =  "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 201512 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 201512) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201512 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 201512) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
			List<Drawdown> result=q.list();
			
			

		} catch (SQLException | ParseException | JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		session.getTransaction().commit();

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

	private void getEndOfMonth(List<Drawdown> result) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int i = 200401;
		int j = 0;
		int orDay = 1;
		Date date1;
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			Drawdown drawdown = (Drawdown) iterator.next();
			date1 = df.parse(drawdown.getDrawdownDate());
			int yrmo = drawdown.getYrmo();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			String d = df.format(date1);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DATE);
			if (yrmo == i) {
				if (orDay < day) {
					orDay = day;
				}
			}
		}
		System.out.println(orDay);

		// String empDate[] = new String[12];
		/**
		 * ArrayList<String> empDate = new ArrayList<String>();
		 * 
		 * 
		 * 
		 * for (int i = 2004; i < 2006; i++) { for (Iterator iterator =
		 * result.iterator(); iterator.hasNext();) { Drawdown drawdown =
		 * (Drawdown) iterator.next(); if
		 * (!drawdown.getDrawdownDate().equals("")) { Date date1; try { date1 =
		 * df.parse(drawdown.getDrawdownDate()); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(date1); String d =
		 * df.format(date1); int month = cal.get(Calendar.MONTH) + 1; if
		 * (month==1) { System.out.println(month+"d"+d); } else {
		 * 
		 * } } catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * } } }
		 */
		/*
		 * for (Iterator iterator = result.iterator(); iterator.hasNext();) {
		 * Drawdown drawdown = (Drawdown) iterator.next(); try { if
		 * (drawdown.getDrawdownDate().equals("")) { }else{ Date date1 =
		 * df.parse(drawdown.getDrawdownDate()); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(date1); String d =
		 * df.format(date1); int month = cal.get(Calendar.MONTH) + 1;
		 * switch(month){ case 1: empDate.add(arg0)=; case 2:
		 * empDate[1]=df.format(date1); case 3: empDate[2]=df.format(date1);
		 * case 4: empDate[3]=df.format(date1); case 5:
		 * empDate[4]=df.format(date1); case 6: empDate[5]=df.format(date1);
		 * case 7: empDate[6]=df.format(date1); case 8:
		 * empDate[7]=df.format(date1); case 9: empDate[8]=df.format(date1);
		 * case 10: empDate[9]=df.format(date1); case 11:
		 * empDate[10]=df.format(date1); case 12: empDate[11]=df.format(date1);
		 * } } } catch (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } return empDate;
		 */
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

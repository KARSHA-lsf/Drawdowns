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
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Drawdown;
import model.Sys_CLM_CumulativeLMC;
import model.Sys_CLM_EndofMonthLMC;
import model.cummulative;

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

		// eoflossmc();
		try {
			cummulativeLoassMakt();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void eoflossmc() {
		db_connections dbconnection = new db_connections();

		SessionFactory SFact = new Configuration().configure()
				.buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();

		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		/** end of the month loss market capitalization **/
		// Sys_CLM_EndofMonthLMC obj = new Sys_CLM_EndofMonthLMC();
		try {

			ResultSet rset = dbconnection
					.selectData("SELECT DISTINCT CAPM_resid_date,YRMO_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ORDER BY CAPM_resid_date");

			ArrayList<String> eofdate = getEndOfMonthDates(rset);

			String all_drawdown = "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 201412 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 201412) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 201412) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
			List<Drawdown> result = q.list();

			ArrayList<Double> eofvalue = getEndOfMonthvalues(result, eofdate);

			for (int i = 0; i < eofdate.size(); i++) {
				Sys_CLM_EndofMonthLMC obj = new Sys_CLM_EndofMonthLMC();
				obj.setLmcdate(eofdate.get(i));
				obj.setValue(eofvalue.get(i));
				session.save(obj);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/** end of the month loss market capitalization **/

		session.getTransaction().commit();
	}

	private ArrayList<Double> getEndOfMonthvalues(List<Drawdown> result,
			ArrayList<String> eofdate) {
		double empValue[] = new double[1000];
		ArrayList<Double> eofvalue = new ArrayList<Double>();
		for (int j = 0; j < eofdate.size(); j++) {
			double x = 0;
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Drawdown drawdown = (Drawdown) iterator.next();
				double returnvalue = (drawdown.getReturnValue().doubleValue() - 1)
						* drawdown.getMarketCapitalization();
				if (drawdown.getDrawdownDate().equals(eofdate.get(j))) {
					x = x + returnvalue;
				}
			}
			empValue[j] = x;
		}

		for (int j = 0; j < empValue.length; j++) {
			if (empValue[j] != 0) {
				eofvalue.add(empValue[j]);
			}
		}
		return eofvalue;
	}

	private ArrayList<String> getEndOfMonthDates(ResultSet rset) {
		ArrayList<String> edate = new ArrayList<>();
		String[] eofdate = new String[1000];

		int j = 0;
		int y = 200401;
		try {
			while (rset.next()) {
				if (!rset.getString("CAPM_resid_date").equals("")) {
					String d1 = rset.getString("CAPM_resid_date");
					if (rset.getInt("YRMO_date") == y) {
						eofdate[j] = d1;
					} else {
						if ((j + 1) % 12 == 0) {
							y = y + 89;
							j++;
						} else {
							y = y + 1;
							j = j + 1;
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < eofdate.length; i++) {
			if (eofdate[i] != null) {
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

	// Cumulative loss market Capitalization calculation code

	protected void cummulativeLoassMakt() throws JSONException, ParseException {
		db_connections dbconnection = new db_connections();

		SessionFactory SFact = new Configuration().configure()
				.buildSessionFactory();
		Session session = SFact.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();

		for (int k = 2004; k < 2005; k++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = k + "-01-01";
			//String endDate = k + "-12-31";

			Calendar cal1 = new GregorianCalendar();
			Calendar cal2 = new GregorianCalendar();

			Date date = sdf.parse(k+"-01-01");
			cal1.setTime(date);
			date = sdf.parse(k+"-12-31");
			cal2.setTime(date);
			int a = (int) ((cal2.getTime().getTime() -cal1.getTime().getTime()) / (1000 * 60 * 60 * 24));
	        String allDate[][] = new String[a+1][2];
				
	        for(int i=0;i<=a;i++){
	            allDate[i][0]=startDate;
	            allDate[i][1]=Integer.toString(0);
	            Calendar c = Calendar.getInstance();
	            c.setTime(sdf.parse(startDate));
	            c.add(Calendar.DATE, 1);
	            startDate = sdf.format(c.getTime());
	            
	        }
			

			//String all_drawdown = "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO LIKE '"+ k
					//+ "%' AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo LIKE '"
					//+ k
					//+ "%') AS B ON A.PERMNO = B.permno ) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date LIKE '"
					//+ k
					//+ "%' AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo LIKE '"
					//+ k
					//+ "%') AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			
	        
	        /*String all_drawdown="SELECT X.permno AS permno,X.yrmo AS yrmo,X.drawdownValue AS drawdownValue,X.drawdownDate AS drawdownDate,X.marketCapitalization AS marketCapitalization,X.returnValue AS returnValue FROM (SELECT *, @counter := @counter +1 AS counter FROM (select @counter :=0) AS initvar, (SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO LIKE '"
	        +k+"%' AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo LIKE '"
	        +k+"%') AS B ON A.PERMNO = B.permno ) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM(SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date LIKE '"
	        +k+"%' AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo LIKE '"
	        +k+"%') AS LON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo)  as p ) AS Xwhere counter <= (10/100 * @counter) ORDER BY X.drawdownDate";*/
	        String all_drawdown="SELECT * FROM sys_blu_top10_losess WHERE yrmo LIKE '%"+k+"%' ORDER BY drawdownDate";
			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));

			@SuppressWarnings("unchecked")
			List<Drawdown> results = q.list();
			int count = 0;
			int emptyCount = 0;
			System.out.println("Results.size is :" + results.size());
			String multiArry[][] = new String[results.size()][2];

			// multiple drawdownValue and marketCapitalization
			for (Iterator<Drawdown> iterator = results.iterator(); iterator.hasNext();) {

				Drawdown data = (Drawdown) iterator.next();

				if (!data.getDrawdownDate().isEmpty()) {

					multiArry[count][0] = data.getDrawdownDate();
					BigDecimal marketcapitalization = new BigDecimal(data.getMarketCapitalization());
					BigDecimal lossmarket = data.getDrawdownValue().multiply(marketcapitalization);
					multiArry[count][1] = String.valueOf(lossmarket);
					count = count + 1;
				} else {
					emptyCount = emptyCount + 1;
				}
			}

			//for(int i=0;i< multiArry.length - emptyCount - 1;i++){
				//System.out.println(multiArry[i][0]+"=="+multiArry[i][1]);
			//}
			String secondSortArray[][] = new String[365][2];
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			int countforSecondArray = 0;
			BigDecimal AdditionofValue = new BigDecimal(0);
			BigDecimal zero = BigDecimal.ZERO;

			for (int i = 0; i < multiArry.length - emptyCount - 1; i++) {
				//System.out.println(multiArry[i][0]+"=="+AdditionofValue);
				//System.out.println(multiArry[i][0]+"=="+multiArry[i][1]);
				AdditionofValue = ((new BigDecimal(multiArry[i][1])).add(AdditionofValue));
				//System.out.println(AdditionofValue);
				if (multiArry[i][0].equals(multiArry[i + 1][0])) {

					secondSortArray[countforSecondArray][0] = multiArry[i][0];
					secondSortArray[countforSecondArray][1] = AdditionofValue.toString();

				} else {
					secondSortArray[countforSecondArray][0] = multiArry[i][0];
					secondSortArray[countforSecondArray][1] = AdditionofValue.toString();
					countforSecondArray = countforSecondArray + 1;
					AdditionofValue = zero;
				}

			}
			//for(int i=0;i<countforSecondArray+1;i++){
				//System.out.println(secondSortArray[i][0]+"=="+secondSortArray[i][1]);
			//}
			// ----------------------------index query
			// output---------------------------------------//
			String index = "SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"
					+ k
					+ "%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"
					+ k
					+ "%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end";
			String arrayYear[] = new String[12];

			try {
				ResultSet set = dbconnection.selectData(index);
				int yearCount = 0;
				while (set.next()) {
					arrayYear[yearCount] = set.getString("Index_dates");
					yearCount = yearCount + 1;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			BigDecimal cummilativeValue = new BigDecimal(0);
			String CumalativeArray[][] = new String[countforSecondArray + 1][2];
			
						
			for (int i = 0; i < arrayYear.length; i++) {

				for (int j = 0; j < countforSecondArray + 1; j++) {
					
					Date d1;
					Date d2;
					Date d3;
					Date d4;
					Date d6;
					try {

						if (i == 0) {
							
							d1 = (Date) format.parse(arrayYear[i]);
							d2 = (Date) format.parse(arrayYear[i + 1]);
							d3 = (Date) format.parse(secondSortArray[j][0]);
							if (d3.before(d1)) {
								cummilativeValue = new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
								CumalativeArray[j][0] = secondSortArray[j][0];
								CumalativeArray[j][1] = cummilativeValue.toString();
							} else if (d3.equals(d1) ||( d3.after(d1)&& d3.before(d2))) {
								
								cummilativeValue = new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
								CumalativeArray[j][0] = secondSortArray[j][0];
								CumalativeArray[j][1] = cummilativeValue.toString();							
										
							}else if(d3.equals(d2)){
								cummilativeValue = new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
								CumalativeArray[j][0] = secondSortArray[j][0];
								CumalativeArray[j][1] = cummilativeValue.toString();
							}
							else {
								cummilativeValue = zero;
							}
						} else if (0 < i && i <= 10) {
							d1 = (Date) format.parse(arrayYear[i]);
							d2 = (Date) format.parse(arrayYear[i + 1]);
							d3 = (Date) format.parse(secondSortArray[j][0]);
						
							if (d3.equals(d2) || (d3.after(d1) && d3.before(d2))) {

								cummilativeValue = new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
								CumalativeArray[j][0] = secondSortArray[j][0];
								CumalativeArray[j][1] = cummilativeValue.toString();
										
							}
							else {
								cummilativeValue = zero;
							}
						}
						
						else if (i == 11) {
							d4 = (Date) format.parse(arrayYear[i]);
							d6 = (Date) format.parse(secondSortArray[j][0]);

							if ( d6.after(d4)) {
								cummilativeValue = new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
								CumalativeArray[j][0] = secondSortArray[j][0];
								CumalativeArray[j][1] = cummilativeValue.toString();
	
							}
						}

					} catch (ParseException e) {

						e.printStackTrace();
					}
				}
			}
			
			/*for(int i=0;i<=allDate.length-1;i++){
				for(int j=0;j<CumalativeArray.length;j++){
					Date dateA=(Date)format.parse(allDate[i][0]);
					Date dateB=(Date)format.parse(CumalativeArray[j][0]);
					if(dateA.before(dateB)){
						allDate[i][1]=Integer.valueOf(0).toString();
					}
				}
			}*/
			for(int i=0;i<CumalativeArray.length;i++){
				System.out.println(CumalativeArray[i][0]+"==="+CumalativeArray[i][1]);
			}
			
			/*JSONArray jary=new JSONArray();
			  for(int i=0;i<CumalativeArray.length;i++){
				 JSONObject jobj=new
				 JSONObject(); jobj.put("Date",CumalativeArray[i][0]);
				  jobj.put("value",CumalativeArray[i][1]); jary.put(jobj);
			}
			 
			 System.out.println(jary);*/
			/*
			 * for(int i=0;i<CumalativeArray.length;i++){ cummulative
			 * cummulativeObject=new cummulative();
			 * cummulativeObject.setDate(CumalativeArray[i][0]);
			 * cummulativeObject.setValue(new
			 * BigDecimal(CumalativeArray[i][1]));
			 * session.save(cummulativeObject); }
			 */

		}

		tx.commit();
		session.close();
	}

}
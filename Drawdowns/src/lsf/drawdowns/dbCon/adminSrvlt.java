package lsf.drawdowns.dbCon;

import java.io.IOException;
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
//import org.json.JSONException;
//import org.json.JSONObject;


import model.Drawdown;
import model.Sys_CLM_CumulativeLMC;
import model.Sys_CLM_EndofMonthLMC;
import model.Sys_CLM_EndofMonthLMC_top_ten;

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

	//	eoflossmc();
	//	cummulativeLoassMakt();
			end_of_month_LMC();
			
	}

	
	private void end_of_month_LMC() {
		// TODO Auto-generated method stub
	//	String sql ="SELECT SUM(x.rmc) FROM (SELECT a.permno,a.yrmo,(a.value1*b.value1) as rmc FROM (SELECT permno,yrmo,value1 FROM caaf_marketcapitalization WHERE yrmo=201501) a INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo = 201501) b ON a.permno = b.permno) x INNER JOIN  (SELECT DISTINCT(PERMNO_date) FROM capm_drawdowns_date WHERE DATE(CAPM_resid_date) BETWEEN '2014-12-16' AND '2015-01-15' ORDER BY CAPM_resid_date ) y ON x.permno = y.PERMNO_date";
		
		SessionFactory SFact = new Configuration().configure().buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();
		
		
		
		db_connections db_con = new db_connections();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> indexDates = get_index_dates();
		ArrayList<Double> endOfMonthVales = new ArrayList<Double>();
		ArrayList<String> endOFMonthDates = new ArrayList<String>();
		ArrayList<Double> endOfMonthVales_top_ten = new ArrayList<Double>();
		ArrayList<String> endOFMonthDates_top_ten = new ArrayList<String>();
		
	//	ArrayList<Double> cumulative_LMC_top_ten_values = new ArrayList<Double>();
	//	ArrayList<String> cumulative_LMC_top_ten_date = new ArrayList<String>();
		
		for (int i = 0; i < indexDates.size()-1; i++) {
				String d1 = indexDates.get(i);
				String d2 = indexDates.get(i+1);
				String[] parts = d2.split("-");
				int yrmo =  Integer.valueOf(parts[0]+""+parts[1]);

				String sql ="SELECT SUM(x.rmc) FROM (SELECT a.permno,a.yrmo,(a.value1*b.value1) as rmc FROM (SELECT permno,yrmo,value1 FROM caaf_marketcapitalization WHERE yrmo='"+yrmo+"') a INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo = '"+yrmo+"') b ON a.permno = b.permno) x INNER JOIN  (SELECT DISTINCT(PERMNO_date) FROM capm_drawdowns_date WHERE DATE(CAPM_resid_date) BETWEEN '"+d1+"' AND '"+d2+"' ORDER BY CAPM_resid_date ) y ON x.permno = y.PERMNO_date";
				String sql_top_ten="SELECT SUM(a.marketCapitalization*b.value1) FROM (SELECT PERMNO,YRMO,marketCapitalization,CAPM_resid_date FROM sys_top10_losess WHERE DATE(CAPM_resid_date) BETWEEN '"+d1+"' AND '"+d2+"' ORDER BY CAPM_resid) AS a INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo='"+yrmo+"') AS b ON a.PERMNO=b.permno";
				//String sql_LMC ="SELECT SUM(LOSSMcap) FROM sys_top10_losess WHERE DATE(CAPM_resid_date) BETWEEN '"+d1+"' AND '"+d2+"' ORDER BY CAPM_resid";
				
				try {
					Date eDate = dateFormat.parse(d2);
					Calendar calendar = Calendar.getInstance();
			        calendar.setTime(eDate);
			        calendar.add(Calendar.MONTH, 1);
			        calendar.set(Calendar.DAY_OF_MONTH, 1);
			        calendar.add(Calendar.DATE, -1);
			        Date lastDayOfMonth = calendar.getTime();
			        
					ResultSet rset = db_con.selectData(sql);
					if(rset.next()){
						endOfMonthVales.add(rset.getDouble("SUM(x.rmc)"));
						endOFMonthDates.add(dateFormat.format(lastDayOfMonth));
					}
					
					ResultSet rset_top_ten = db_con.selectData(sql_top_ten);
					if(rset_top_ten.next()){
						endOfMonthVales_top_ten.add(rset_top_ten.getDouble("SUM(a.marketCapitalization*b.value1)"));
						endOFMonthDates_top_ten.add(dateFormat.format(lastDayOfMonth));
					}
					
			        /*
					ResultSet rset_lmc_top_ten = db_con.selectData(sql_LMC);
					if(rset_lmc_top_ten.next()){
						System.out.println(rset_lmc_top_ten.getDouble("SUM(LOSSMcap)")+" date "+dateFormat.format(lastDayOfMonth));
						
					}
					*/
				} catch (SQLException | ParseException e) {
					e.printStackTrace();
				}
		}
		
		for (int i = 0; i < endOfMonthVales.size(); i++) {
			Sys_CLM_EndofMonthLMC obj = new Sys_CLM_EndofMonthLMC();
			obj.setLmcdate(endOFMonthDates.get(i));
			obj.setValue(endOfMonthVales.get(i));		
			session.save(obj);
		}
		for (int i = 0; i < endOfMonthVales_top_ten.size(); i++) {
			Sys_CLM_EndofMonthLMC_top_ten obj = new Sys_CLM_EndofMonthLMC_top_ten();
			obj.setLmcdate(endOFMonthDates_top_ten.get(i));
			obj.setValue(endOfMonthVales_top_ten.get(i));		
			session.save(obj);
		}
		session.getTransaction().commit();
	}

	private ArrayList<String> get_index_dates() {
		// TODO Auto-generated method stub
		ArrayList<String> tmp_indexDates = new ArrayList<String>();
		db_connections dbconnection = new db_connections();
		try {
			ResultSet rset = dbconnection.selectData("SELECT date_withyear FROM caaf_drawdownend WHERE DATE(date_withyear) > '2003-11-30' AND date_withyear != '0000-00-00' AND permno_end=0 ORDER BY date_withyear");
			while(rset.next()){
				tmp_indexDates.add(rset.getString("date_withyear"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmp_indexDates;
	}

	/*
	public void eoflossmc(){
		
		db_connections dbconnection = new db_connections();

		SessionFactory SFact = new Configuration().configure().buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();


		try {
			
			ResultSet rset = dbconnection.selectData("SELECT DISTINCT CAPM_resid_date,YRMO_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ORDER BY CAPM_resid_date");
			
			ArrayList<String> eofdate = getEndOfMonthDates(rset);	
				
			String all_drawdown =  "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 201412 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 201412) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 201412 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 201412) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
			List<Drawdown> result=q.list();	
			
			ArrayList<Double> eofvalue = getEndOfMonthvalues(result,eofdate);
			
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
		
		
		session.getTransaction().commit();
		
	}
	*/

	/*
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
		return eofvalue;
	}
	*/

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


	//Cumulative loss market Capitalization calculation code
	
		protected void cummulativeLoassMakt() {
			db_connections dbconnection = new db_connections();
		
			SessionFactory SFact = new Configuration().configure().buildSessionFactory();
			Session session = SFact.openSession();
			org.hibernate.Transaction tx = session.beginTransaction();
			
			for(int k=2004;k<=2014;k++){
				
				String all_drawdown = "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO LIKE '"+k+"%' AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo LIKE '"+k+"%') AS B ON A.PERMNO = B.permno ) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date LIKE '"+k+"%' AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo LIKE '"+k+"%') AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
				
				SQLQuery q = session.createSQLQuery(all_drawdown);
				q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
				
				
				@SuppressWarnings("unchecked")
				
				List<Drawdown> results = q.list();
				int count=0;
				int emptyCount=0;
				System.out.println("Results.size is :"+results.size());
				String multiArry[][] =new String[results.size()][2];
			
				//multiple drawdownValue and marketCapitalization 
				for (Iterator<Drawdown> iterator = results.iterator(); iterator.hasNext();) {
					
					Drawdown data = (Drawdown) iterator.next();
					
					if(!data.getDrawdownDate().isEmpty()){
						
						multiArry[count][0]=data.getDrawdownDate();
						BigDecimal marketcapitalization=new BigDecimal(data.getMarketCapitalization());
						BigDecimal lossmarket=data.getDrawdownValue().multiply(marketcapitalization);
						multiArry[count][1]=String.valueOf(lossmarket);
						count=count+1;
					}
					else{
						emptyCount=emptyCount+1;
					}	
				}
			
				
				String secondSortArray[][]=new String[365][2];
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				int countforSecondArray=0;
				BigDecimal AdditionofValue=new BigDecimal(0);
				BigDecimal zero=BigDecimal.ZERO;
				
				for(int i=0;i<multiArry.length-emptyCount-1;i++){	
							
					if(multiArry[i][0].equals(multiArry[i+1][0])){					
						AdditionofValue=((new BigDecimal(multiArry[i][1])).add(AdditionofValue));
						secondSortArray[countforSecondArray][0]=multiArry[i][0];
						secondSortArray[countforSecondArray][1]=AdditionofValue.toString();		
					}				
					else{
						countforSecondArray=countforSecondArray+1;
						AdditionofValue=zero;					
					}				
				}
									
				//----------------------------index query output---------------------------------------//
				String index="SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '"+k+"%') AS  A  JOIN (SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '"+k+"%') AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end";
				String arrayYear[]=new String[12];
				
				try {
					ResultSet set = dbconnection.selectData(index);			
					int yearCount=0;
					while(set.next()){
						arrayYear[yearCount]=set.getString("Index_dates");
						yearCount=yearCount+1;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				BigDecimal cummilativeValue=new BigDecimal(0);
				String CumalativeArray[][]=new String[countforSecondArray+1][2];
				
				
				
				for(int i=0;i<arrayYear.length;i++){
		
					for(int j=0;j<countforSecondArray+1;j++){
						Date d1;
						Date d2;
						Date d3;
						Date d4;
						Date d6;
						try {	
							
							if(i==0){
								d1 = (Date)format.parse(arrayYear[i]);
								d2=(Date)format.parse(arrayYear[i+1]);						
								d3=(Date)format.parse(secondSortArray[j][0]);
								if(d3.before(d1)){
									cummilativeValue=new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
									CumalativeArray[j][0]=secondSortArray[j][0];
									CumalativeArray[j][1]=cummilativeValue.toString();
								}
								else if(d3.equals(d1)||d3.after(d1)&&d3.before(d2)){
									cummilativeValue=new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
									CumalativeArray[j][0]=secondSortArray[j][0];
									CumalativeArray[j][1]=cummilativeValue.toString();
									
								}
								else{
									cummilativeValue=zero;
								}
							}
							else if(0<i && i<= 10){
								
								d1 = (Date)format.parse(arrayYear[i]);
								d2=(Date)format.parse(arrayYear[i+1]);						
								d3=(Date)format.parse(secondSortArray[j][0]);
								//pwr.println(d3);
								if(d3.equals(d1)||d3.after(d1)&&d3.before(d2)){
									
									cummilativeValue=new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
									CumalativeArray[j][0]=secondSortArray[j][0];
									CumalativeArray[j][1]=cummilativeValue.toString();
								}						
								else{
									cummilativeValue=zero;
								}
							}
							
							else if(i==11){
								d4 = (Date)format.parse(arrayYear[i]);						
								d6 = (Date)format.parse(secondSortArray[j][0]);
														
								if(d6.equals(d4)||d6.after(d4)){	
									
									cummilativeValue=new BigDecimal(secondSortArray[j][1]).add(cummilativeValue);
									CumalativeArray[j][0]=secondSortArray[j][0];
									CumalativeArray[j][1]=cummilativeValue.toString();	
									
								}
							}				
							
						} catch (ParseException e) {
						
							e.printStackTrace();
						}					
					}
				}
							
				for(int i=0;i<CumalativeArray.length;i++){			
					Sys_CLM_CumulativeLMC cummulativeObject=new Sys_CLM_CumulativeLMC();
					cummulativeObject.setDate(CumalativeArray[i][0]);
					cummulativeObject.setValue(new BigDecimal(CumalativeArray[i][1]));			
					session.save(cummulativeObject);
				}
					
			}
			
			tx.commit();
			session.close();
		}
}

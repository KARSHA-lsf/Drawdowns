package lsf.drawdowns.dbCon;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Drawdown;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		db_connections dbconnection = new db_connections();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		PrintWriter pwr = response.getWriter();
		
		SessionFactory SFact = new Configuration().configure().buildSessionFactory();
		Session session = SFact.openSession();
		session.beginTransaction();
		
		for(int k=2004;k<=2014;k++){
			
			
			String all_drawdown = "SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO LIKE '"+k+"%' AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo LIKE '"+k+"%') AS B ON A.PERMNO = B.permno ) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date LIKE '"+k+"%' AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo LIKE '"+k+"%') AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";
			//String all_drawdown ="SELECT x.PERMNO AS permno,x.YRMO AS yrmo,x.CAPM_resid AS drawdownValue,y.CAPM_resid_date AS drawdownDate,x.value1 AS marketCapitalization,y.returnValue FROM ( SELECT A.PERMNO, A.YRMO, A.CAPM_resid, B.value1 FROM ( SELECT * FROM capm_drawdowns_results WHERE YRMO BETWEEN 200401 AND 200512 AND HORIZON = 1) AS A INNER JOIN ( SELECT permno, yrmo, value1 FROM caaf_marketcapitalization WHERE yrmo BETWEEN 200401 AND 200512) AS B ON A.PERMNO = B.permno AND A.YRMO = B.yrmo) AS x INNER JOIN (SELECT K.PERMNO_date,K.YRMO_date,K.CAPM_resid_date,L.value1 AS returnValue FROM (SELECT PERMNO_date,YRMO_date,CAPM_resid_date FROM capm_drawdowns_date WHERE YRMO_date  BETWEEN 200401 AND 200512 AND HORIZON = 1 ) AS K INNER JOIN (SELECT permno,yrmo,value1 FROM caaf_returns WHERE yrmo  BETWEEN 200401 AND 200512) AS L ON K.PERMNO_date=L.permno AND K.YRMO_date=L.yrmo) AS y ON y.PERMNO_date = x.PERMNO AND y.YRMO_date = x.yrmo ORDER BY y.CAPM_resid_date";

			SQLQuery q = session.createSQLQuery(all_drawdown);
			q.setResultTransformer(Transformers.aliasToBean(Drawdown.class));
			
			
			@SuppressWarnings("unchecked")
			
			List<Drawdown> results = q.list();
			int count=0;
			int emptyCount=0;
			System.out.println("Results.size is :"+results.size());
			String multiArry[][] =new String[results.size()][2];
		
			
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
			
			JSONArray cummitativeJarray=new JSONArray();
			for(int i=0;i<CumalativeArray.length;i++){
				
				JSONObject cummilativeJobject=new JSONObject();
				
				try {
					cummilativeJobject.put("Value", CumalativeArray[i][1]);
					cummilativeJobject.put("Date", CumalativeArray[i][0]);
					
				} catch (JSONException e) {				
					e.printStackTrace();
				}
				
				cummitativeJarray.put(cummilativeJobject);
				
			}			
			pwr.println(cummitativeJarray);

		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
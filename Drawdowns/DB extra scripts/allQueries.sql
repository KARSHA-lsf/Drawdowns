/*INDEX_VW_RETURN: */
 SELECT * FROM crsp_valueweightedreturns WHERE Crsp_date like '2004%'
/*dataget_method :*/
 SELECT PERMNO,CAPM_resid_D FROM sys_scatter_plot WHERE YRMO =200401 ORDER BY CAPM_resid
 /*rangedata_method*/
 SELECT x.PERMNO_date AS PERMNO,x.CAPM_resid_date AS CAPM_resid_D FROM 
 (SELECT PERMNO_date,YRMO_date,CAPM_resid_date,@counter := @counter +1 AS counter FROM 
 (select @counter:=0) AS initvar,capm_drawdowns_date WHERE capm_drawdowns_date.HORIZON=1 AND YRMO_date=200401) AS x , (SELECT PERMNO,YRMO,CAPM_resid FROM capm_drawdowns_results WHERE capm_drawdowns_results.HORIZON=1 AND YRMO=200401) AS y WHERE counter <= (10/100 * @counter) AND  x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO ORDER BY y.CAPM_resid
/*indexdata_method*/
 SELECT B.date_withyear AS Index_dates,A.value1 AS Index_values FROM 
 ( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '2004%') AS  A  JOIN 
(SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '2004%')
AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end

/*summarydata_method*/
/*summarydata_method for caaf parameter*/
SELECT YEAR(date_withyear) AS date,COUNT(YEAR(date_withyear)) AS count FROM caaf_drawdownend WHERE date_withyear GROUP BY YEAR(date_withyear)
/*summary graph for capm*/
SELECT YEAR(CAPM_resid_date) AS date,COUNT(YEAR(CAPM_resid_date)) AS count FROM capm_drawdowns_date WHERE CAPM_resid_date GROUP BY YEAR(CAPM_resid_date)

/*eofMonthLMC*/
select * from Sys_CLM_EndofMonthLMC where lmcdate like '%2004%'
select * from Sys_CLM_EndofMonthLMC_top_ten where lmcdate like '%2004%'
select * from Sys_CLM_EndofMonthLMC where lmcdate like '2004%'

/*cumulativeLossMkp*/

select all_dates,cum from sys_10precnt_2004to2014 where all_dates like '%2004%'
select * from cummulative where date like '%2004%'
select * from cummulative where date like '%2004%'

/*clmIndexPercentage*/

SELECT B.date_withyear AS Index_dates,ABS(A.value1) AS Index_values FROM 
( SELECT  permno, value1,yrmo FROM caaf_drawdowns WHERE  permno=0 AND yrmo LIKE '2004%') AS  A  JOIN 
(SELECT  permno_end,date_withyear,yrmo_end FROM  caaf_drawdownend WHERE permno_end=0 AND yrmo_end LIKE '2004%')
AS  B ON A.permno=B.permno_end AND A.yrmo=B.yrmo_end 


/*Perm_History_Method*/

SELECT CAPM_resid_D,CAPM_resid FROM 
sys_scatter_plot  where PERMNO =75257 AND YRMO LIKE '2004%'

			

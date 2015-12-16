/* scatter plot data table.*/
CREATE TABLE sys_scatter_plot AS
SELECT x.PERMNO_date AS PERMNO, x.YRMO_date AS YRMO,x.CAPM_resid_date AS CAPM_resid_D,y.CAPM_resid AS CAPM_resid
FROM
(SELECT PERMNO_date,YRMO_date,CAPM_resid_date 
FROM capm_drawdowns_date 
WHERE capm_drawdowns_date.HORIZON=1) AS x
JOIN
(SELECT PERMNO,YRMO,CAPM_resid 
FROM capm_drawdowns_results 
WHERE capm_drawdowns_results.HORIZON=1) AS y
ON x.PERMNO_date = y.PERMNO AND x.YRMO_date=y.YRMO  
ORDER BY y.CAPM_resid;

/*top losses */

/*top 10% losses */
CREATE TABLE sys_top10_losess(
SELECT G.PERMNO as PERMNO, G.YRMO AS YRMO, G.CAPM_resid AS CAPM_resid,G.CAPM_resid_date AS CAPM_resid_date,G.marketCapitalization AS marketCapitalization,
G.LossMcap AS LOSSMcap FROM (
(SELECT  C.PERMNO,C.YRMO,C.CAPM_resid,C.CAPM_resid_date,D.value1 as marketCapitalization,C.CAPM_resid*D.value1 AS LossMcap FROM
(SELECT B.PERMNO,B.YRMO,B.CAPM_resid,A.CAPM_resid_date
	FROM
		(SELECT PERMNO_date,YRMO_date,CAPM_resid_date
		FROM capm_drawdowns_date USE INDEX (capm_date_indx)
		WHERE HORIZON=1 and YRMO_date ) AS A
INNER JOIN
		(SELECT PERMNO,YRMO,CAPM_resid
		FROM capm_drawdowns_results USE INDEX (capm_rslt_indx)
		WHERE HORIZON=1 and YRMO) AS B
ON A.PERMNO_date=B.PERMNO AND A.YRMO_date=B.YRMO ) AS C
INNER JOIN
caaf_marketcapitalization AS D USE INDEX(caaf_mcap_indx)
ON C.PERMNO = D.permno AND C.YRMO = D.yrmo 
ORDER BY C.CAPM_resid
LIMIT 34825 )
UNION 
(SELECT  C.PERMNO,C.YRMO,C.CAPM_resid,C.CAPM_resid_date,D.value1 as marketCapitalization, C.CAPM_resid*D.value1 AS LossMcap FROM
(SELECT B.PERMNO,B.YRMO,B.CAPM_resid,A.CAPM_resid_date
	FROM
		(SELECT PERMNO_date,YRMO_date,CAPM_resid_date
		FROM capm_drawdowns_date USE INDEX (capm_date_indx)
		WHERE HORIZON=1 and YRMO_date ) AS A
INNER JOIN
		(SELECT PERMNO,YRMO,CAPM_resid
		FROM capm_drawdowns_results USE INDEX (capm_rslt_indx)
		WHERE HORIZON=1 and YRMO) AS B
ON A.PERMNO_date=B.PERMNO AND A.YRMO_date=B.YRMO ) AS C
INNER JOIN
caaf_marketcapitalization AS D USE INDEX(caaf_mcap_indx)
ON C.PERMNO = D.permno AND C.YRMO = D.yrmo 
ORDER BY lossMcap
LIMIT 34825) )AS G
WHERE G.YRMO BETWEEN 200401 AND 201412)

/*all the dates */
CREATE TABLE sys_all_dates1925to2015(
select * from 
(select adddate('1926-01-01',t4.i*10000 + t3.i*1000 + t2.i*100 + t1.i*10 + t0.i) all_dates from
 (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,
 (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,
 (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,
 (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,
 (select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v
where all_dates between '1926-01-01' and '2015-12-31');






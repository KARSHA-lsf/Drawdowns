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

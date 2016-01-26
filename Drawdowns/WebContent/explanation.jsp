<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html class="no-js">

<head>
<title>KARSHA-Drawdowns</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<meta charset="utf-8">
<link rel="stylesheet" href="assets/jquery-ui.css">
<link rel="stylesheet" href="css/footer-distributed.css">
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
	$(function() {
		$("#accordion").accordion({
			heightStyle : "content"
		});
	});
</script>
</head>

<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">KARSHA-Drawdowns</a>
				<div class="nav-collapse collapse">

					<ul class="nav">
						<li class="active"><a href="index.jsp">Home</a></li>
						<li><a href="top10losses.jsp" style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="pattern.jsp">Pattern</a></li>
						<li><a href="definitions.jsp">Definitions</a></li>
						<li><a href="explanation.jsp">Explanations</a></li>
						<li><a href="about.jsp">About</a></li>
						<!-- <li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li> -->
						
						
					</ul>
				</div>

			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12"
				style="border: 1px solid LightSeaGreen; background-color: white">
				<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="col-lg-12">
							<h3 class="page-header" style="text-align:center">Explanations for 2004 -2014 Significant Losses and Pattern Templates</h3>
						</div>
						<p>&nbsp;&nbsp;&nbsp;&nbsp;This dataset comprises a Drawdown dataset of individual equities that are included in the S&P 500 Index. We consider data from 2004 to 2014. 
									A (monthly) Drawdown represents the day where the equity experiences the lowest price for that month. 
									The Drawdown data was corrected (CAPM_resid Drawdown); the correction included a factor that is based on the correlation of the equity price with the S&P 500 index historically.
									We use the Top 10++% Significant Losses for our analysis. This is determined as a UNION of the Top 10% Drawdown values and the Top 10% Loss of market capitalization values.
									Market capitalization = Price * Count_of_Outstanding_Shares for the individual equity.</p>

						<p>Consider the following chart with Blue and Orange and Red and Green bars:
									Insert figure.<a href="definitions.jsp" target="_blank">Definitions</a></p>
									
						<p>
						<b>Blue:</b>		Cumulative loss of market capitalization using most significant CAPM_resid drawdown individual equities.</p>
						<p>
						<b>Orange:</b>	S&P 500 Index drawdown.</p>
						<p><b>Red:</b>     	End_of_month Loss of market capitalization for blue equities.</p>
						<p><b>Green:</b> 		End_of_month S&P 500 Index Market capitalization.</p>

						<p><b>SMALL and LARGE market capitalization threshold was calculated using the yearly range of values of market capitalization.</b></p>
						<center><img src="bootstrap/img/1.PNG"></center>
						</p>
						
						<p>Based on the values of the blue, orange, red and green bars we identified the following four
						 templates or patterns:
						 <b>PUSHDOWN / LOSS NO IMPACT / NO IMPACT / NON ALIGN</b></p>
						 
						 
						 <ul>
							<li><font color="red"><b>PUSHDOWN: </b> Blue bar is LARGE NEGATIVE. Red bar is negative. Green bar is LARGE NEGATIVE.
							 The Blue loss of market capitalization did not fully recover by the end of the month (red is negative) 
							 and this pushed down the index.</font></li>
							 
							<li><b>LOSS NO IMPACT:</b> HIGH LOSS LOW GAIN Blue bar is LARGE NEGATIVE. 
							Red bar is small negative or small positive. Green bar is close to zero or small positive.</li>
							
							<li><b>NO IMPACT:</b>LOW LOSS LOW GAIN
								Blue bar is small negative. Red bar is close to zero or small positive. Green bar is close to zero or small positive.
								The Blue loss of market capitalization recovered by the end of the month. The index did not show much gain. </li>
							<li><b>LLHG: LOW LOSS HIGH GAIN</b>
								Blue bar is small negative -- this is compared to all the other blue bars for that year, e..g, the PUSHDOWN cases.</li>
						 	
						 	<li><font color="red"><b>NON ALIGN</b>
								Blue bar is LARGE NEGATIVE but green bar is POSITIVE.</font></li>
						 
						 </ul>
						
					</div>
						
			</div> 
					
		</div>
	</div>
		
				<!-- /container -->
			
	
	
	<div id="footer">
  <div class="container">
    <p style="text-align:center">Lanka Software Foundation &copy; 2015</p>
  </div>
</div>
</body>
</html>

</body>
</html>

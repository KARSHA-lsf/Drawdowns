<!DOCTYPE html>
<html class="no-js">

<head>
<title>KARSHA-Drawdowns</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="bootstrap/css/c3.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<meta charset="utf-8">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
$(function () {
	$("#accordion").accordion({
		heightStyle : "content",
		active : <%=request.getParameter("Q")%>-2004
	});
});
</script>

<script>
$(function () {
	$("#area").load("yearly_analisis.jsp #area > *");
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
						<li><a href="index.jsp">Home</a></li>
						<li><a href="about.jsp">About</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="yearly_analysis_percentages.jsp?Q=2004&M=03" style="text-align: center">test</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>

	<%
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October",
				"November", "December" };
		String[] monthDate = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
	%>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2" id="sidebar">
				<div id="area">
				
				<div id="accordion">
					<%
						for (int k = 2004; k < 2015; k++) {
					%>
					<h6>
						Year
						<%
						out.println(k);
					%>
					</h6>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=<%=k%>&M=<%=monthDate[i]%>"> <%out.println(k + "-" + monthDate[i]);%>
						</a></li>
						<%
							}
						%>
					</ul>
					<%
						}
					%>
				</div>
				</div>
			</div>

			<div class="span10"
				style="border: 1px solid LightSeaGreen; background-color: white">
				<div>
					<div class "row-fluid" style="margin: 30px 30px 30px">
						<h1>Monthly Analisis</h1>
					</div>
				</div>
				<div>
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">
							Scatter plot for 
							<%=request.getParameter("Q")+" "+months[Integer.valueOf(request.getParameter("M"))-1]%></h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<div id="scatter_plot"></div>

					</div>
				</div>
				<div>
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">
							Index drowdown for year
							<%=request.getParameter("Q")%></h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<div id="barIndex"></div>

					</div>
				</div>
				<div>
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">
							Loss Market Capitalization : 
							<%=request.getParameter("Q")%></h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<div id="multihistogram"></div>

					</div>
				</div>
				<!-- /.row -->
			</div>

			<!-- /.row -->

		</div>
	</div><script>
		//divide data from url to catogories
		$(document).ready(
				function y() {
					
					
					
					var urlscatter = "dataGet?M="+"<%=request.getParameter("M")%>&Q="+"<%=request.getParameter("Q")%>";
					var i,p;
					var High,High_Medium,Medium,Medium_low,low = [];
					var Arr,PermNo=[],Perm_date = [];
					var H_PermNo=[],HM_PermNo =[],M_PermNo = [],ML_PermNo = [],L_PermNo = [];
					var H_Perm_date=[],HM_Perm_date =[],M_Perm_date = [],ML_Perm_date = [],L_Perm_date = [];
					$.ajax({
		                type: 'GET',
		                url: urlscatter,
		                dataType: 'json',
		                success: function (data) {
		                	
		               	 var x = parseInt(data.length/5);
		               	 
		               	High = $.grep(data, function(n, i){
		               	  return (i < x);
		               	  });
		               	 
		               	High_Medium = $.grep(data, function(n, i){
		                 	  return (i<2*x && i>=x );
		                 	  });
		               	Medium = $.grep(data, function(n, i){
		               	  return (i<3*x && i>=2*x );
		               	  });
		               	Medium_low = $.grep(data, function(n, i){
		                 	  return (i<4*x && i>=3*x );
		                 	  });
		               	low = $.grep(data, function(n, i){
		                 	  return (i>=4*x );
		                 	  });
		               	//generate perm no and date according to catogories
		               	function Perm_Gen(Arr,PermNo,Perm_date){		        			
		               		for(p=0;p<Arr.length;p++)
		               		{
		               			PermNo[p]=Arr[p].permno;
		               			Perm_date[p]=Arr[p].capm_date;               			
		               		}   		
		                  }		               
		               	Perm_Gen(High,H_PermNo,H_Perm_date);              
		               	Perm_Gen(High_Medium,HM_PermNo,HM_Perm_date);
		               	Perm_Gen(Medium,M_PermNo,M_Perm_date);
		               	Perm_Gen(Medium_low,ML_PermNo,ML_Perm_date);
		               	Perm_Gen(low,L_PermNo,L_Perm_date);
		               	
		               	//ready variable to json output
		               	var Ready_output={"High":H_PermNo,"High_x":H_Perm_date,"HighMedium":HM_PermNo,"HighMedium_x":HM_Perm_date,"Medium":M_PermNo,
		               			"Medium_x":M_Perm_date,"MediumLow":ML_PermNo,"MediumLow_x":ML_Perm_date,"Low":L_PermNo,"Low_x":L_Perm_date};
		               		
		               	//console.log(Ready_output);
		               	//call method in graph.js to draw scatter-plot
		               	drawScatterPlot(Ready_output,<%=request.getParameter("Q")%>,<%=request.getParameter("M")%>);
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
					
					var urlindex = "indexData?Q="+"<%=request.getParameter("Q")%>";
					$.ajax({
		                type: 'GET',
		                url: urlindex,
		                dataType: 'json',
		                success: function (data) {
		                	
		               	//console.log(data);
		               	
		               	drawIndex(data);
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
					
					var x = "test_getSet?Q="+"<%=request.getParameter("Q")%>";
					$.ajax({
		                type: 'GET',
		                url: x,
		                dataType: 'json',
		                success: function (data) {
		                	
		               	//console.log(data);
		               	
		               	drawLossMcGraph(data);
		               	
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
				});
				
		</script>
	
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
</body>
</html>

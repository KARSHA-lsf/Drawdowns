<!DOCTYPE html>
<html class="no-js">

<head>
<meta charset="utf-8">
<title>KARSHA-Drawdowns</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="bootstrap/css/c3.css" rel="stylesheet">
<link rel="stylesheet" href="assets/jquery-ui.css">
<script src="js/jquery-1.10.2.js"></script>


<script>
$(function () {
	$("#accordion").accordion({
		heightStyle : "content",
		active : <%=request.getParameter("Q")%>-2004
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
						<li><a href="index.jsp">Home</a></li>
						<li><a href="about.jsp">About</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li>
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
							href="yearly_analisis.jsp?Q=<%=k%>&M=<%=monthDate[i]%>"> <%
 	out.println(k + "-" + monthDate[i]);
 %>
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
	</div>
	
	<script>
		//divide data from url to catogories
		$(document).ready(
				function() {
var urlscatter = "dataGet?M="+"<%=request.getParameter("M")%>&Q="+"<%=request.getParameter("Q")%>";
					
					$.ajax({
		                type: 'GET',
		                url: urlscatter,
		                dataType: 'json',
		                success: function (data) {
		                	var Ready_output = sccaterPlot_dataPreprocess(data);          
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
		               	//drawLossMcGraph(data);
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
		                	
<<<<<<< HEAD
		               	console.log(data);
		               	//drawIndex(data);
=======
		               	//console.log(data);
		               	
>>>>>>> branch 'master' of https://github.com/Karsha-Project-LSF/Drawdowns.git
		               	drawLossMcGraph(data);
		               	
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
<<<<<<< HEAD
					var urlindex = "test?Q="+"<%=request.getParameter("Q")%>";
					$.ajax({
		                type: 'GET',
		                url: urlindex,
		                dataType: 'json',
		                success: function (data) {
		                	
		               	//console.log(data);
		               	
		               	//drawIndex(data);
		               	drawLossMcGraph(data);
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
					
=======
>>>>>>> branch 'master' of https://github.com/Karsha-Project-LSF/Drawdowns.git
				});
				
		</script>
	
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
	<script src="js/jquery-ui.js"></script>
</body>
</html>

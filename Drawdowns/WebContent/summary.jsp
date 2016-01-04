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
	$(function() {
		$("#accordion").accordion({
			heightStyle : "content",
			active : <%=request.getParameter("Q")%>-2004
		});
	});
</script>
</head>

<body>
<div id="loading" style="display:table-cell; vertical-align:middle; text-align:center"><img id="loading-image" src='demo_wait.gif'/><br>Loading..</div>


	<script>
		//divide data from url to catogories
		$(document).ready(function capmgraph() {
			$(document).ajaxStart(function(){
		        $("#loading").css("display", "block");
		    });
		    $(document).ajaxComplete(function(){
		        $("#loading").css("display", "none");
		    });
			var urlcapm = "summaryData?D=capm";
			$.ajax({
				type : 'GET',
				url : urlcapm,
				dataType : 'json',
				success : function(data) {
					console.log(data);
					drawSummaryGraph(data, "#histocapm");
				},
				error : function(data, error) {
					console.log(error);
				},
				async : false
			});
			var urlcaff = "summaryData?D=caff";
			$.ajax({
				type : 'GET',
				url : urlcaff,
				dataType : 'json',
				success : function(data) {
					console.log(data);
					drawSummaryGraph(data, "#histocaff");
				},
				error : function(data, error) {
					console.log(error);
				},
				async : false
			});
		});
	</script>



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
						<li><a href="top10losses.jsp" style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a></li>
						<li class="active"><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="about.jsp">About</a></li>
						<!-- <li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li> -->
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
<!-- 			<div class="span2" id="sidebar"> -->
				<%-- <div id="accordion">
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

				</div> --%>

			</div>


			<div class="span12"
				style="border: 1px solid LightSeaGreen; background-color: white">
				<div>
					<div class "row-fluid" style="margin: 30px 30px 30px">
						<h1>Summary</h1>
					</div>
				</div>
				<div>
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">Summary graph for CAPM resid</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<div id="histocapm"></div>

					</div>
				</div>
				<div>
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">Summary graph for CAAF drawdowns</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<div id="histocaff"></div>

					</div>
				</div>
				<!-- /.row -->
			</div>

		</div>
		<script src="bootstrap/js/bootstrap.min.js"></script>
		<script src="bootstrap/js/c3.js"></script>
		<script src="bootstrap/js/d3.min.js"></script>
		<script src="js/graphs.js"></script>
</body>
</html>

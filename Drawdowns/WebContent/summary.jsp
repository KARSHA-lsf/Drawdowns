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
<meta charset="utf-8">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
	$(function() {
		$("#accordion").accordion({
			heightStyle : "content"
		});
	});
</script>
</head>

<body>


	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		//divide data from url to catogories
		$(document).ready(function capmgraph() {
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
						<li><a href="about.jsp">About</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>

	<%
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October",
				"November", "December" };
	%>


	<div class="container-fluid">

		<div class="row-fluid">
			<div class="span2" id="sidebar">
				<div id="accordion">
					<h3>2004 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2005 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2006 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2007 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2008 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2009 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2010 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2011 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2012 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2013 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>
					<h3>2014 Year</h3>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=2006&M<%=i%>"> <%
 	out.println(months[i]);
 %>
						</a></li>
						<%
							}
						%>
					</ul>

					<ul class="nav-collapse">
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
					</ul>

				</div>

			</div>
			<div class="span10"
				style="border: 1px solid LightSeaGreen; background-color: white">
				<div class "row-fluid" style="margin: 30px 30px 30px">
					<h1>KARSHA-Drawdowns</h1>
				</div>
			</div>
		</div>
	</div>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
</body>
</html>
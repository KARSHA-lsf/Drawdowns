<!DOCTYPE html>
<html class="no-js">

<head>
<meta charset="utf-8">
<title>KARSHA-Drawdowns</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">
<link href="bootstrap/css/c3.css" rel="stylesheet">
<link href="bootstrap/css/sb-admin-2.css" rel="stylesheet">
<link rel="stylesheet" href="assets/jquery-ui.css">
<script src="js/jquery-1.10.2.js"></script>


<script>
	$(function() {
		$("#tabs").tabs();
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
						<li><a href="advance_filter.jsp?Q=2004&M=03"
							style="text-align: center">Advance Filter</a></li>
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
			<div class="span2">

				<div class="navbar-default sidebar"
					style="border: 1px solid LightSeaGreen; background-color: white;">

					<ul class="nav">
						<%
							for (int i = 2004; i < 2015; i++) {
						%>
						<li
							style="text-align: center; width: 150px; margin-left: 10px; padding-top: 10px; padding-bottom: 10px; padding-left: 15px; padding-right: 15px;">
							<a style="font-size: 16px" class="#" href=#?Q=<%=i %>> Year <%
								out.println(i);
							%>
						</a>
						</li>
						<%
							}
						%>
					</ul>

				</div>

			</div>

			<div class="span10"
				style="border: 1px solid LightSeaGreen; background-color: white; margin-left: 10px;">
				<div>

					<div id="tabs">
						<ul>
							<%
								for (int i = 0; i < 12; i++) {
									String tab = "#tab" + months[i];
									String tabid = "tab" + months[i];
							%>
							<li><a style="font-size: 14px;" id="ta<%=months[i]%>"
								href="<%=tab%>"><%=months[i]%></a></li>
							<%
								}
							%>
						</ul>
						<%
							for (int i = 2004; i < 2015; i++) {
								String tab = "tab" + i;
						%>
						<div id="<%=tab%>">
							<div class="row">
								<div class="col-lg-12" style="margin: 30px 30px 30px">
									<div id="scatter_plot<%=i%>"></div>
								</div>
							</div>
						</div>
						<%
							}
						%>
					</div>
				</div>


				<!-- /.row -->
			</div>

			<!-- /.row -->

		</div>
	</div>



	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
	<script src="js/jquery-ui.js"></script>
</body>
</html>

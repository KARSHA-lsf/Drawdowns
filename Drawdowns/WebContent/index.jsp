
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
<link rel="stylesheet"
	href="assets/jquery-ui.css">
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
						<li><a href="about.jsp">About</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>

	<%
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October",
				"November", "December" };
		String[] monthDate = { "01", "02", "03", "04", "05",
				"06", "07", "08", "09", "10",
				"11", "12" };
	%>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2" id="sidebar">
				<div id="accordion">
					<%
						for (int k = 2004; k < 2015; k++) {
					%>
					<h7>
						Year
						<%
						out.println(k);
					%>
					</h7>
					<ul class="nav-collapse">
						<%
							for (int i = 0; i < 12; i++) {
						%>
						<li class="nav-collapse" style="text-align: center"><a
							style="font-family: Arial; color: blue; text-decoration: none"
							href="yearly_analisis.jsp?Q=<%=k %>&M=<%=monthDate[i]%>"> <% out.println(k+"-"+monthDate[i]); %>
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
				<div class "row-fluid" style="margin: 30px 30px 30px">
					<h1>KARSHA-Drawdowns</h1>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$.post("IndexSrvlt","")
			.error(function() {
				//alert("there is error while sending data to server");
			});
	</script>
</body>
</html>

</body>
</html>
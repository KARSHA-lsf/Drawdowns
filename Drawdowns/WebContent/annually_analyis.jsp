
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html class="no-js">

<head>
<meta charset="utf-8">
<title>KARSHA-Drawdowns</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
	media="screen">
<link href="assets/styles.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="assets/jquery-ui.css">
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
$(function() {
  $( "#tabs" ).tabs();
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
						<li><a href="advance_filter.jsp?Q=2004&M=03"
							style="text-align: center">Advance Filter</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly
								Analysis</a></li>
					</ul>
				</div>

			</div>
		</div>
	</div>

	


	<div class="container-fluid">
		<div class="row-fluid">

			<div class="span12"
				style="border: 1px solid LightSeaGreen; background-color: white">
				<div class "row-fluid" style="margin: 30px 30px 30px">
					<h3>Yearly Analysis [ 2004 - 2014 ]</h3>
				</div>
				<div id="tabs">
				
					<ul>
					<% for(int i=2004;i<2015;i++){ %>
						<li><a href="#tabs-1"><%=i%></a></li>
					<% } %>
					</ul>
					<div id="tabs-1">
						<p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo
							a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet
							mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa
							et lorem. Mauris dapibus lacus auctor risus. Aenean tempor
							ullamcorper leo. Vivamus sed magna quis ligula eleifend
							adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum.
							Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede
							varius sollicitudin. Sed ut dolor nec orci tincidunt interdum.
							Phasellus ipsum. Nunc tristique tempus lectus.</p>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>

</body>
</html>
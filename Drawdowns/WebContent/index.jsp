
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
						<li><a href="Monthly_analysis.jsp">Monthly Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
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
				<div class="row-fluid" style="margin: 30px 30px 30px">
					<h1 align="center">KARSHA-Drawdowns</h1>
				</div>
						<div class="span4">
						<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:260px;height:150px">
							<h2 class="icon soul">
								<a href="top10losses.jsp">Top 10% Losses</a>
							</h2>
<!-- 							<p>Every great brand needs a good logo. This is my bread and -->
<!-- 								butter.</p> -->
						</div>
						</div>
						<div class="span4">
							<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:260px;height:150px">
							<h2 class="icon web">
								<a href="annually_analyis.jsp">Yearly Analysis</a>
							</h2>
<!-- 							<p>Websites are the new store fronts. A nice one can bring -->
<!-- 								more business.</p> -->
						</div>
					</div>
						<div class="span3">
							<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:270px;height:150px">
							<h2 class="icon shirt">
								<a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a> 
							</h2>
<!-- 							<p>Need a poster, a book cover, a label for a booze or beer -->
<!-- 								bottle? I can do that too!</p> -->
						</div>
						</div>
					</div> 
					<hr>
				</div>
			</div>
		
				<!-- /container -->
			
	
	<script type="text/javascript">
		$.post("IndexSrvlt", "").error(function() {
			//alert("there is error while sending data to server");
		});
	</script>
</body>
</html>

</body>
</html>

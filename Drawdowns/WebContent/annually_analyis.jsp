
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

						<div class="row">
							<div class="col-lg-12" style="margin: 30px 30px 30px">
								<div id="scatter_plot"></div>

							</div>
						</div>
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
	
	<script>
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
					
				});
				
		</script>
	
</body>
</html>

</body>
</html>
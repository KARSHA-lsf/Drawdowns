
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
<link href="bootstrap/css/c3.css" rel="stylesheet">
<link rel="stylesheet" href="assets/jquery-ui.css">
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
var Dr_value=20,LossMcap_value=20,tab=2004,data_init;
	$(function() {
		$("#tabs").tabs();
	});
</script>

<style type="text/css">
	#Dr_slider .ui-slider-range { background: #ef2929; }
</style>
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
				<div class="span4">
					<div class="block" style="border: 1px none LightSeaGreen;">
						<h3 style="padding-left: 10px;">
						Yearly Analysis [ 2004 - 2014 ]</h3>		
					</div>
				</div>
				<div class="span3">
					<br>
						<div class="col-sm-3">
							Drawdown Value Top : <span id="Dr_value"  style="font-weight:bold;"></span>
						</div>
						<div class="col-sm-3">
							<div id="Dr_slider"></div>
						</div>
				</div>
				<div class="span3">
					<br>
						<div class="col-sm-3">
							LossMcap Value Top : <span id="LossMcap_value" style="font-weight:bold;"></span>
						</div>
						<div class="col-sm-3">
							<div id="LossMcap_slider"></div>
						</div>
				</div>
				</div>
			</div>
		
				<div class "row-fluid">
				
				<div id="tabs">
					<ul>
						<% for(int i=2004;i<2015;i++){String tab = "#tab"+i;String tabid = "tab"+i; %>
						<li><a id="ta<%=i%>" href="<%=tab%>"><%=i%></a></li>
						<% } %>
					</ul>
					<% for(int i=2004;i<2015;i++){String tab = "tab"+i;%>
					<div id="<%=tab%>">
						<div class="row">
						<div class="col-lg-12" style="margin: 30px 30px 30px">
							<div id="scatter_plot<%=i%>"></div>
						</div>
						</div>
						<script type="text/javascript">
							$("#ta<%=i%>").click(function(){
								tab =<%=i%>;
								drw_filtered_SCAT(<%=i%>,Dr_value,LossMcap_value);	
							});
						</script>
					</div>
					<% } %>
					<script type="text/javascript">
					function drw_filtered_SCAT(tab,Dr_value,LossMcap_value){
						//console.log("lll :"+Dr_value+" : "+LossMcap_value);
						var urlscatter = "GetAnnualData?yrmo="+tab+"&Dr_top="+Dr_value+"&LossMcap_top="+LossMcap_value;
						//console.log(urlscatter);
						$.ajax({
							type : 'GET',
							url : urlscatter,
							dataType : 'json',
							success : function(data) {
								data_init = data;
								draw_me(data);
								},
								error : function(data, error) {
									console.log(error);
								},
								async : false
						});
					}
					function draw_me(data){
						var Ready_output = sccaterPlot_dataPreprocess_withTopFilter(data,Dr_value,LossMcap_value);
						//call method in graph.js to draw scatter-plot
						drawScatterPlot_yearly(
							Ready_output,tab , 01,
					 		'#scatter_plot'+tab);
					}
					</script>
				</div>
				</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		//divide data from url to catogories
		$(document)
			.ready(
				function() {
					$("#Dr_value").text(Dr_value);
					 
					  $(function() {
					    $( "#Dr_slider" ).slider({
					    		min:1,
					    		max:50,
					    		value:Dr_value,
					    		slide: function( event, ui ) {
					                $( "#Dr_value" ).text( ui.value + " %" );
					                Dr_value=ui.value;
					                draw_me(data_init);
					             }}			
					   	);
					    $( "#LossMcap_slider" ).slider({
					    	max:50,
					    	min:1,
					    	value:LossMcap_value,
							slide: function( event, ui ) {
					            $( "#LossMcap_value" ).text( ui.value + " %" );
					            LossMcap_value=ui.value;
					         }		
					    });
					    
					  });
					 
					
					
					drw_filtered_SCAT(2004,Dr_value,LossMcap_value);
			});
	</script> 
	
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
	

</body>
</html>

</body>
</html>

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
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/jquery-ui.js"></script>

<link href="bootstrap/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="bootstrap/js/bootstrap-toggle.min.js"></script>

<script>
var Dr_value=100,LossMcap_value=20,tab=2004,data_init;
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
						<li><a href="index.jsp">Home</a></li>
						<li class="active"><a href="top10losses.jsp" style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a></li>
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
				<div class="span5">
					<div class="block" style="border: 1px none LightSeaGreen;">
						<h3 style="padding-left: 10px;">
					Top 10% Losses [ 2004 - 2014 ]</h3>		
					</div>
				</div>
				
				<div class="span3">
					<br>	
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
					
					<div class="col-lg-12" style="margin: 30px 30px 30px">
						<h4 class="page-header">Loss Market Capitalization</h4>		
						
						<div class="col-lg-12" style="margin: 30px 30px 30px">
							<b>Scale :</b> <input type="checkbox" id="btnScal" data-toggle="toggle" data-on="Local" data-off="Global" data-onstyle="success" data-offstyle="info" data-height="20">				
							<script>
  								$(function() {
   				 					$('#btnScal').bootstrapToggle({
      									on: 'Enabled',
      									off: 'Disabled'
    								});
   				 				$('#btnScal').change(function() {
   				 			      if($(this).prop('checked')){
   				 			    	draw_cumulativeGraph(tab,"G");
   				 			    	
   				 			      }
   				 			      else{
   				 			    	draw_cumulativeGraph(tab,"L");
   				 			      }
   				 			    })
  								})
							</script>
						</div>		
												
						<div id="multihistogram"></div>					
					</div>
							
		
					<% for(int i=2004;i<2015;i++){String tab = "tab"+i;%>
					<div id="<%=tab%>">
						<div class="row">
							<div class="col-lg-12" style="margin: 30px 30px 30px">
								<div class="span9">
									<h2 class="page-header">Market Behavior Individual Level</h2>	
										
									<!-- <div id="loading" style="display:table-cell; vertical-align:middle; text-align:center"><img id="loading-image" src='demo_wait.gif'/><br>Loading..</div>
 -->								</div>
								<div class="span3">
									<div class="col-sm-3">
											Drawdown Value Top : <span id="Dr_value"  style="font-weight:bold;"></span>
											<div id="Dr_slider"></div>							
										</div>
								</div>
							<div id="scatter_plot<%=i%>"></div>
							<br>
<!-- 							<div id="wait" style="display:table-cell; vertical-align:middle; text-align:center"><img src='demo_wait.gif'/><br>Loading..</div> -->
							</div>
						</div>
						<script type="text/javascript">
							$("#ta<%=i%>").click(function(){
								tab =<%=i%>;
								drw_filtered_SCAT(<%=i%>,Dr_value,LossMcap_value);	
								draw_indexdata(tab);
								draw_cumulativeGraph(tab);
							});
						</script>
					</div>
					<% } %>  
					<div class="col-lg-12" style="margin: 30px 30px 30px">
							<h4 class="page-header">
							Index drowdown
							</h4>
							<div id="barIndex"></div>
					</div>
					 
					<div id="dialog" title="Basic Dialog">
						<div id="permhistory"></div>
					<script type="text/javascript">
					function drw_filtered_SCAT(tab,Dr_value,LossMcap_value){
						//console.log("lll :"+Dr_value+" : "+LossMcap_value);
						var urlscatter = "TopLossesAnnualData?yrmo="+tab+"&Dr_top="+Dr_value+"&LossMcap_top="+LossMcap_value;
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
	<div id="more_details"></div>
	<script>
		//divide data from url to catogories
		$(document)
			.ready(
				function() {
					/* $(document).ajaxStart(function(){
				        $("#loading").css("display", "block");
				    });
				    $(document).ajaxComplete(function(){
				        $("#loading").css("display", "none");
				    }); */
					$("#Dr_value").text(Dr_value+ " %");
					$("#LossMcap_value").text(LossMcap_value+ " %");
					  $(function() {
					    $( "#Dr_slider" ).slider({
					    		min:1,
					    		max:100,
					    		value:Dr_value,
					    		slide: function( event, ui ) {
					                $( "#Dr_value" ).text( ui.value + " %" );
					                Dr_value=ui.value;
					                draw_me(data_init);
					             }}			
					   	);
					   
					  });					
					drw_filtered_SCAT(2004,Dr_value,LossMcap_value);
					//draw_indexdata(2004);
					draw_cumulativeGraph(2004,"L");
					draw_indexdata(2004);
			});
		function draw_indexdata(year){
			var urlindex = "indexData?Q="+year;
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
		}
		function draw_cumulativeGraph(year,status){
			var x = "test_getSet?Q="+year+"&T=top10Precent";
			console.log(x);
			$.ajax({
	            type: 'GET',
	            url: x,
	            dataType: 'json',
	            success: function (data) {
	            	
	           	//console.log(data);
	           	
	           	drawLossMcGraphTopTen(data,status);
	           	
	            },
	            
	            error: function (data,
	                    error) {
	            	console.log(error);
	            },
	            async: false
	        });
		}
	</script> 
	
	<script src="js/graphs.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	
	

</body>
</html>

</body>
</html>

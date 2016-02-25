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
		$( "#Dr_slider2004" ).slider({
    		min:1,
    		max:100,
    		value:Dr_value,
    		slide: function( event, ui ) {
                $( "#Dr_value2004" ).text( ui.value + " %" );
                Dr_value=ui.value;
                draw_me(data_init);
             }}			
   	);
   
	});
</script>

<style type="text/css">
#Dr_slider .ui-slider-range {
	background: #ef2929;
}
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
						<li class="active"><a href="top10losses.jsp"
							style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly
								Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly
								Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="pattern.jsp">Pattern</a></li>
						<li><a href="definitions.jsp">Definitions</a></li>
						<li><a href="explanation.jsp">Explanation</a></li>
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
						<h3 style="padding-left: 10px;">Top 10% Losses [ 2004 - 2014 ]</h3>
					</div>
				</div>

				<div class="span3">
					<br>
				</div>
			</div>
		</div>

		<divclass "row-fluid">
				
				<div id="tabs">
				
					
					<script>
						var scale="L";
					</script>
					<ul>
						<%
							for (int i = 2004; i < 2015; i++) {
								String tab = "#tab" + i;
								String tabid = "tab" + i;
						%>
						<li><a id="ta<%=i%>" href="<%=tab%>"><%=i%></a></li>
						<%
							}
						%>
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
   				 			    			scale="G";
   				 			    			console.log("scale is : "+scale);
   				 			      		}
   				 			      		else{
   				 			    			draw_cumulativeGraph(tab,"L");
   				 			    			scale="L";
   				 			    			console.log("scale is : "+scale);
   				 			      		}
   				 			    	});
   				 
  								});
							</script>
						</div>		
												
						<div id="multihistogram"></div>	
						<br>
						<div class="row">
						  <div class="span3" style="border:2px solid #000000"><center><h6>January</h6></center>
						  <div id="M1"></div>
						  </div>
						  <div id="M2" class="span3" style="border:2px solid #000000"><center><h6>February</h6></center></div>
						  <div id="M3" class="span3" style="border:2px solid #000000"><center><h6>March</h6></center></div>
						  <div id="M4" class="span3" style="border:2px solid #000000"><center><h6>April</h6></center></div>
						</div>
						<br>
						<div class="row">
						  <div id="M5" class="span3" style="border:2px solid #000000"><center><h6>May</h6></center></div>
						  <div id="M6" class="span3" style="border:2px solid #000000"><center><h6>June</h6></center></div>
						  <div id="M7" class="span3" style="border:2px solid #000000"><center><h6>July</h6></center></div>
						  <div id="M8" class="span3" style="border:2px solid #000000"><center><h6>August</h6></center></div>
						</div>
						<br>
						<div class="row">
						  <div id="M9" class="span3" style="border:2px solid #000000"><center><h6>September</h6></center></div>
						  <div id="M10" class="span3" style="border:2px solid #000000"><center><h6>October</h6></center></div>
						  <div id="M11" class="span3" style="border:2px solid #000000"><center><h6>November</h6></center></div>
						  <div id="M12" class="span3" style="border:2px solid #000000"><center><h6>December</h6></center></div>
						</div>
						
									
					</div>
							
		
					<% for (int i = 2004; i < 2015; i++) {
						String tab = "tab" + i;
					%>
					<div id="<%=tab%>">
						<div class="row">
							<div class="col-lg-12" style="margin: 30px 30px 30px">
								
									<h3 class="page-header">Market Behavior Individual Level</h3>	
										
									<!-- <div id="loading" style="display:table-cell; vertical-align:middle; text-align:center"><img id="loading-image" src='demo_wait.gif'/><br>Loading..</div>
 -->								
								
											Drawdown Value Top : <span id="Dr_value<%=i%>"  style="font-weight:bold;"></span>
											<div id="Dr_slider<%=i%>"></div>
															
									
								<form>
								  	
 									<input type="radio" onclick="drw_filtered_SCAT(<%=i%>,Dr_value,LossMcap_value)" name="gender" value="permno" checked> Permno |
  									<input type="radio" onclick="drw_Naics_SCAT(<%=i%>,Dr_value,LossMcap_value)" name="gender" value="naics"> Naics |
  									<input type="radio" onclick="drw_mcap_SCAT(<%=i%>,Dr_value,LossMcap_value)" name="gender" value="mcap"> MarketCapitalization  
								</form>	
								
							<div id="scatter_plot<%=i%>"></div>
							<br>
<!-- 							<div id="wait" style="display:table-cell; vertical-align:middle; text-align:center"><img src='demo_wait.gif'/><br>Loading..</div> -->
							</div>
						</div>
						
																
						<script type="text/javascript">
						$(function() {
							$( "#Dr_slider<%=i%>" ).slider({
					    		min:1,
					    		max:100,
					    		value:Dr_value,
					    		slide: function( event, ui ) {
					                $( "#Dr_value<%=i%>" ).text( ui.value + " %" );
					                Dr_value=ui.value;
					                draw_me(data_init);
					             }}			
					   	);
						});
							$("#ta<%=i%>").click(function(){
								
								Dr_value = 100;
								$("#Dr_value<%=i%>").text(Dr_value+ " %");
								tab =<%=i%>;
									drw_filtered_SCAT(<%=i%>,Dr_value,LossMcap_value);	
									draw_indexdata(tab);
									console.log("scale ekaaa : "+scale);
									draw_cumulativeGraph(tab,scale);
									drw_Naics_monthly(tab); 
																				
							});
							
							
						</script>
					</div>
					
					<%
											}
										%> 
										<br> 
					<div class="col-lg-12" style="margin: 30px 30px 30px">
							<h4 class="page-header">
							Index drowdown
							</h4>
							<div id="barIndex"></div>
					</div>
					 
					<div id="dialog" title="Basic Dialog">
						<div id="permhistory"></div>
					<div id = "reddialog">
						<div id = "red"></div>
					
					</div>
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
					 		'#scatter_plot'+tab,'permno');
					}
					function draw_menaics(data) {
						var Ready_output = sccaterPlot_dataPreprocess_withTopFilter(data,Dr_value,LossMcap_value);
						//call method in graph.js to draw scatter-plot
						console.log();
						drawScatterPlot_yearly_naics(
							Ready_output,tab , 01,
					 		'#scatter_plot'+tab);
					}
					function draw_me_mcap(data,max){
						var Ready_output = sccaterPlot_dataPreprocess_withTopFilter(data,Dr_value,LossMcap_value);
						//call method in graph.js to draw scatter-plot
						drawScatterPlot_yearly(
							Ready_output,tab , 01,
					 		'#scatter_plot'+tab,'Market Capitalization - millions $',max);
					}
					function drw_mcap_SCAT(tab,Dr_value,LossMcap_value){
						//console.log("lll :"+Dr_value+" : "+LossMcap_value);
						var urlscatter = "scattermcaptop10?yrmo="+tab+"&Dr_top="+Dr_value+"&LossMcap_top="+LossMcap_value;
						//console.log(urlscatter);
						$.ajax({
							type : 'GET',
							url : urlscatter,
							dataType : 'json',
							success : function(data) {
								var max = data[0].permno;
								
								for(i=1;i<data.length;i++){
									
								 	 if(data[i].permno>max){
										max = data[i].permno;
									} 
																		
								}
								data_init = data;
								draw_me_mcap(data,max);
								},
								error : function(data, error) {
									console.log(error);
								},
								async : false
						});
					}
					function drw_Naics_SCAT(tab,Dr_value,LossMcap_value){
						//console.log("lll :"+Dr_value+" : "+LossMcap_value);
						var urlscatter = "TopLossesAnnualData?yrmo="+tab+"&Dr_top="+Dr_value+"&LossMcap_top="+LossMcap_value;
						//console.log(urlscatter);
						$.ajax({
							type : 'GET',
							url : urlscatter,
							dataType : 'json',
							success : function(data) {
								data_init = data;
								draw_menaics(data);
								},
								error : function(data, error) {
									console.log(error);
								},
								async : false
						});
					}
					function drw_Naics_monthly(tab){
						
						var urlscatter = "monthly_mcap?yrmo="+tab;
						
						$.ajax({
							type : 'GET',
							url : urlscatter,
							dataType : 'json',
							success : function(data) {
								/* var max = data.mcap[0];
								
								for(i=1;i<data.naics.length;i++){
									//console.log(data.mcap[i+1]);
																	
									
								 	 if(data.mcap[i]>max){
										max = data.mcap[i];
									} 
																		
								}
								console.log(max); */
								
								draw_month_patterns(data);
								//console.log(data.naics.length);
								},
								error : function(data, error) {
									console.log(error);
								},
								async : false
						});
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
					$("#Dr_value2004").text(Dr_value+ " %");
				    draw_month_patterns(2004);  					
					drw_filtered_SCAT(2004,Dr_value,LossMcap_value);
					//draw_indexdata(2004);
					draw_cumulativeGraph(2004,"L");
					draw_indexdata(2004);
					drw_Naics_monthly(2004);
					
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
	<script src="js/month_pattern.js"></script>


</body>
</html>

</body>
</html>
<!DOCTYPE html>
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

<script>
var Dr_value=20,LossMcap_value=20,tab=2004,data_init;
	$(function() {
		$("#tabs").tabs();
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
						<li><a href="top10losses.jsp" style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
						<li  class="active"><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="about.jsp">About</a></li>
						<!-- <li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li> -->
					</ul>
				</div>

			</div>
		</div>
	</div>

	<%
		String yy = request.getParameter("Q");
		String[] months = {yy,"January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October",
				"November", "December"};
		String[] monthDate = {"01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12"};
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
							style="text-align: center; width: 150px; margin-left: 0px; padding-top: 10px; padding-bottom: 10px; padding-left: 15px; padding-right: 15px;border-bottom: 1px solid #e7e7e7;">
							<a style="font-size: 16px" class="#"
							href="monthly_analysis.jsp?Q=<%=i%>&M=01"> Year <%
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
<div id="loading" style="display:table-cell; vertical-align:middle; text-align:center"><img id="loading-image" src='demo_wait.gif'/><br>Loading..</div>
					<div id="tabs">
						<ul>

							<%
								for (int i = 0; i < 13; i++) {
									String tab = "#tab" + i;
									String tabid = "tab" + i;
							%>
							<li><a style="font-size: 13px;" id="ta<%=i%>"
								href="<%=tab%>"> <%=months[i]%>
							</a></li>
							<%
								}
							%>
						</ul>
						
						
						<%
							for (int j = 0; j < 13; j++) {
								String tab = "tab" + j;
						%>
						
						<div id="<%=tab%>">
						<div class="row">
								<div class="col-lg-12" style="margin: 30px 30px 30px">
									<div id="multihistogram"></div>
  								</div>	
							</div>
							<div class="row">
								<div class="col-lg-12" style="margin: 30px 30px 30px">
									<div id="scatter_plot<%=j%>"></div>
  								</div>	
							</div>
							
							<script type="text/javascript">
							$("#ta<%=j%>").click(function(){
								y =<%=request.getParameter("Q")%>;
								m =<%=j%>;
								b = '#scatter_plot'+<%=j%>;
								console.log(y,m,b);
								if (m<1) {	
									draw_cumulativeGraph();
								}else{
									draw_scatter(y,m,b);
								}
									
							});
						</script>
						</div>
						<%
							}
						%>
						<div id="dialog" title="Basic Dialog">
									<div id="permhistory"></div>
						</div>
						
						
					</div>
				</div>


				<!-- /.row -->
			</div>

			<!-- /.row -->

		</div>
	</div>

	<script type="text/javascript">
	
	$(window).ready(
			function() {
				$(document).ajaxStart(function(){
			        $("#loading").css("display", "block");
			    });
			    $(document).ajaxComplete(function(){
			        $("#loading").css("display", "none");
			    });
				draw_cumulativeGraph();
			});
	function draw_scatter(year,month,bindTo){
		var urlscatter = "dataGet?M="+month+"&Q="+year;
			$
					.ajax({
						type : 'GET',
						url : urlscatter,
						dataType : 'json',
						success : function(data) {
							var Ready_output = sccaterPlot_dataPreprocess(data);
							drawScatterPlot( Ready_output,year,month,bindTo );
						},

						error : function(data, error) {
							console.log(error);
						},
						async : false
					});
		}

	function draw_cumulativeGraph(){
		var x = "test_getSet?Q="+"<%=request.getParameter("Q")%>&T=all";
		$.ajax({
            type: 'GET',
            url: x,
            dataType: 'json',
            success: function (data) {
            	
           	//console.log(data);
           	
           	drawLossMcGraph(data,"G");
           	
            },
            
            error: function (data,
                    error) {
            	console.log(error);
            },
            async: false
        });
	}
	function popup2(d, element) {
		var urlindex = "perm_history?Q="+<%=request.getParameter("Q")%>+ "&P=" + d.value;
		$("#dialog").dialog({
				resizable: true,
				width: 450,
				height: 270,
			    	  
			});
		$('#dialog').dialog('option', 'title', 'Behavior of permno : '+d.value+ ' year '+<%=request.getParameter("Q")%>);
			$.ajax({
		       type: 'GET',
			   url: urlindex,
			   dataType: 'json',
			   success: function (data) {
			        	
			    console.log(data);
			    console.log(d.value);
			    Permno_history_graph(data);
			       	
		        },
			        
			    error: function (data,
			                error) {
			      		console.log(error);
			     },
			        	async: false
			    });
			}
							
	</script>

	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="js/graphs.js"></script>
	<script src="js/jquery-ui.js"></script>
</body>
</html>


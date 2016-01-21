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
<link href="bootstrap/css/c3.css" rel="stylesheet">
<link rel="stylesheet" href="assets/jquery-ui.css">
<script src="js/jquery-1.10.2.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="js/jquery-ui.js"></script>

<link href="bootstrap/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="bootstrap/js/bootstrap-toggle.min.js"></script>
<style type="text/css">
	#Dr_slider .ui-slider-range { background: #ef2929; }
	input,td, textarea {
    max-width: 80px;}
    .borderless td, .borderless th {
    border: none;
	}
	

</style>

</head>
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
						<li><a href="top10losses.jsp" style="text-align: center">Top
								10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly
								Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly
								Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li><a href="about.jsp">About</a></li>
						<!-- <li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li> -->


					</ul>
				</div>

			</div>
		</div>
	</div>

	<div class="container-fluid">
					<script>
						var scale="G";
					</script>
		<div>
			<div style="border: 1px solid LightSeaGreen; background-color: white">

				<!-- <div class="row-fluid" style="margin: 30px 30px 30px">
					<h1 align="center">Patterns</h1>
				</div>
				 -->
				<div class="col-lg-12" style="margin: 10px 10px 10px">
										
							<center><h2>2004-2014 - FIVE pattern templates - Local and Global normalization </h2></center>
							<script>
  								$(function() {
   				 					$('#btnScal').bootstrapToggle({
      									on: 'Enabled',
      									off: 'Disabled'
    								});
   				 				$('#btnScal').change(function() {
   				 			      if($(this).prop('checked')){
   				 			    	scale = "L";
									update();
   				 			      }
   				 			      else{
   				 			    	scale = "G";
   				 			    	update();
   				 			      }
   				 			    })
  								})
							</script>
						</div>	
				<div id="chart"></div>
				
				<div class="row">
					<div class="span0"></div>
					<div class="span8" style="margin: 10px 10px 10px">
						<div style="position: relative; top: 5px;">
							<table  class = "table borderless" >
								<tr>
									<th></th>
									<th>Large Negative %</th>
									<th>Small Negative %</th>
									<th>Mid %</th>
									<th>Small Positive %</th>
									<th>Large Positive %</th>
									<th></th>
								</tr>
								<tr>
									<th>Blue</th>
									<td><input type="text" id="BLN" maxlength="4" size="20"
										value="-20"></td>
									<td><input type="text" id="BSN" maxlength="4" size="20"
										value="-15"></td>
									<td><input type="text" id="BM" maxlength="4" size="5"
										value="0"></td>
									<td><input type="text" id="BSP" maxlength="4" size="5"
										value="25"></td>
									<td><input type="text" id="BLP" maxlength="4" size="5"
										value="50"></td> 
								</tr>
								<tr>
									<th>Red</th>
									<td><input type="text" id="RLN" maxlength="4" size="5"
										value="-30"></td>
									<td><input type="text" id="RSN" maxlength="4" size="5"
										value="-25"></td>
									<td><input type="text" id="RM" maxlength="4" size="5"
										value="0"></td>
									<td><input type="text" id="RSP" maxlength="4" size="5"
										value="25"></td>
									<td><input type="text" id="RLP" maxlength="4" size="5"
										value="50"></td>
								</tr>
								<tr>
									<th>Green</th>
									<td><input type="text" id="GLN" maxlength="4" size="5"
										value="-30"></td>
									<td><input type="text" id="GSN" maxlength="4" size="5"
										value="-25"></td>
									<td><input type="text" id="GM" maxlength="4" size="5"
										value="0"></td>
									<td><input type="text" id="GSP" maxlength="4" size="5"
										value="25"></td>
									<td><input type="text" id="GLP" maxlength="4" size="5"
										value="50"></td>
									
								</tr>
							</table>			
						</div>
						</div>
						<div class="span4" >
							<table  class = "table borderless" >
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td> </td></tr>
								<tr>
								 <td>
								   Scale :<br><input type="checkbox" id="btnScal" class="btn btn-primary" data-toggle="toggle" data-on="Local" data-off="Global" data-onstyle="success" data-offstyle="info" data-height="20" />
								 </td>
								</tr>
								<tr><td></td></tr>
								<tr>
								  <td>
								   <button class="btn btn-default" type="button" onclick="update()">Update</button>
								  </td>
								</tr>
							</table>
								
								
								
						</div>
					</div>

					
						<script>
							$(document).ready(function() {
								var x = "pattern";
								$.ajax({
									type : 'GET',
									url : x,
									dataType : 'json',
									success : function(data) {
										setData(data);
										update();
									},

									error : function(data, error) {
										console.log(error);
									},
									async : false
								});

							});

							/*
								$.getJSON('data/test.json', function(data) {
									setData(data);
									update();
								});
							 */
							var BLN, BSN, BM, BSP, BLP;
							var RLN, RSN, RM, RSP, RLP;
							var GLN, GSN, GM, GSP, GLP;

							function update() {
								BLN = parseInt(document.getElementById("BLN").value);
								BSN = parseInt(document.getElementById("BSN").value);
								BM = parseInt(document.getElementById("BM").value);
								BSP = parseInt(document.getElementById("BSP").value);
								BLP = parseInt(document.getElementById("BLP").value);

								RLN = parseInt(document.getElementById("RLN").value);
								RSN = parseInt(document.getElementById("RSN").value);
								RM = parseInt(document.getElementById("RM").value);
								RSP = parseInt(document.getElementById("RSP").value);
								RLP = parseInt(document.getElementById("RLP").value);

								GLN = parseInt(document.getElementById("GLN").value);
								GSN = parseInt(document.getElementById("GSN").value);
								GM = parseInt(document.getElementById("GM").value);
								GSP = parseInt(document.getElementById("GSP").value);
								GLP = parseInt(document.getElementById("GLP").value);

								if (validations(BLN, BSN, BM, BSP, BLP)
										&& validations(RLN, RSN, RM, RSP, RLP)
										&& validations(GLN, GSN, GM, GSP, GLP)) {
									setPercentages(scale);
									setColorCode(BLN, BSN, BM, BSP, BLP, RLN,
											RSN, RM, RSP, RLP, GLN, GSN, GM,
											GSP, GLP);

								} else {
									window
											.alert("Invalid Inputs. \nPlease Check.");
								}
							}
							function validations(LN, SN, M, SP, LP) {
								var bool = false;
								if (isNaN(LN) || isNaN(SN) || isNaN(M)
										|| isNaN(SP) || isNaN(LP)) {
								} else {
									if (LN >= -100 && LN <= SN && SN <= M
											&& M <= SP && SP < LP && LP <= 100) {
										bool = true;
									}
								}
								return bool;
							}
						</script>
					</div>
				</div>


				<hr>
			</div>
		</div>
	</div>
	<!-- /container -->

<script src="js/grid.js"></script>
<script src="bootstrap/js/c3.js"></script>
<script src="bootstrap/js/d3.min.js"></script>
</body>
</html>

</body>
</html>

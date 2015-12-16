<!DOCTYPE html>
<html class="no-js">
    
    <head>
        <title>KARSHA-Drawdowns</title>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="assets/styles.css" rel="stylesheet" media="screen">
        <meta charset="utf-8">
		<link rel="stylesheet"
		href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

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
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">KARSHA-Drawdowns</a>
                    <div class="nav-collapse collapse">
                       
                        <ul class="nav">
                            <li><a href="index.jsp">Home</a></li>
						<li><a href="top10losses.jsp" style="text-align: center">Top 10% Losses</a></li>
						<li><a href="annually_analyis.jsp" style="text-align: center">Yearly Analysis</a></li>
						<li><a href="monthly_analysis.jsp?Q=2004&M=01">Monthly Analysis</a></li>
						<li><a href="Monthly_analysis.jsp">Monthly Analysis</a></li>
						<li><a href="summary.jsp" style="text-align: center">Summary</a></li>
						<li class="active"><a href="about.jsp">About</a></li>
						<!-- <li><a href="advance_filter.jsp?Q=2004&M=03" style="text-align: center">Advance Filter</a></li> -->
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
                <%-- <div class="span2" id="sidebar">
                    <div id="accordion">
						<%
							for (int k = 2004; k < 2015; k++) {
						%>
						<h6>
							Year
							<%
							out.println(k);
						%>
						</h6>
						<ul class="nav-collapse">
							<%
								for (int i = 0; i < 12; i++) {
							%>
									<li style="text-align: center"><a
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
						
					</div>					 --%>
                	
                
                
				<div class="span12" style="border:1px solid LightSeaGreen;background-color:white" >
					<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="col-lg-12">
							<h1 class="page-header" style="text-align:center">About Us</h1>
						</div>
						<p>The Karsha GC Drawdowns website and tool was developed to visualize an evolving set of 'Granger Causality'
						networks where each network is derived from a pair of financial data streams.</p>
						<p>The Karsha project aims to develop next generation financial cyberinfrastructure 
						tools to support data science for finance.</p>	
						<p>Karsha is supported by the Smith School of Business at the University of Maryland, the Lanka Software Foundation
						and the US National Science Foundation.</p>
					</div>
					
					<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="span3">
							<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:160px;height:100px">
								<img src="bootstrap/img/lsf.jpg">
									<a href="http://opensource.lk/" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<hr style="border-color:#8AC007">
													<span class="pull-left">
														<p>Lanka Software Foundation</p>
													</span><br></br> <span></span>
												</hr>
											</div>
										</div>
										
									</a>
							</div>
						</div>
						
						<div class="span3">
							<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:160px;height:100px">
								<img src="bootstrap/img/ucsc.png">
									<a href="http://www.ucsc.cmb.ac.lk/" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<hr style="border-color:#8AC007">
													<span class="pull-left">
														<p>UCSC</p>
													</span><br></br> <span></span>
												</hr>
											</div>
										</div>
										
									</a>
							</div>
						</div>
						
						<div class="span3">
							<div class="block" style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:160px;height:100px">
								<img src="bootstrap/img/maryland.jpg">
									<a href="http://www.rhsmith.umd.edu/" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<hr style="border-color:#8AC007">
													<span class="pull-left">
														<p>University of Maryland</p>
													</span><br></br> <span></span>
												</hr>
											</div>
										</div>
									</a>
							</div>
						</div>
						<div class="span3">
							<div class="block"  style="border-radius:25px;border:2px solid #8AC007;padding:20px;width:160px;height:100px">
								<img src="bootstrap/img/Nsf.jpg">
									<a href="http://www.nsf.gov/" target="_blank">
										<div class="panel-footer">
											<div class="container5">
												<hr style="border-color:#8AC007">
													<span class="pull-left">
														<p>US National Science Foundation</p>
													</span><br></br> <span></span>
											</div>
											
										</div>
									</a>
							</div>
						</div>
					</div>
		
					<div class "row-fluid" style="margin:30px 30px 30px">
						<p>This is a free and opensource tool released under GNU General Public License. 
							You can access the source code from <a href="https://github.com/yasithadehigama/Drawdowns" target="_blank">here</a></p>
					</div>
					
					<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="col-lg-12">
							<h1 class="page-header" style="text-align:center">Team</h1>
						</div>
					</div>
					
					<!--Information about Team members-->
					
					<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="span4">
							<div class="block" style="border-radius: 10px;color:red">
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/sadun.png">
									</div>
									<h4 style="text-align:center;margin:30px 0px 0px;color:white">Intern Student at LSF</h4>
								</div>
									<a href="https://github.com/Sandunts" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 60px 0px">Sandun Siriwardhana</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						
						<div class="span4">
							<div class="block" style="border-radius: 10px;color:red">
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/yasitha.png">
									</div>
									<h4 style="text-align:center;margin:30px 0px 0px;color:white">Intern Student at LSF</h4>
								</div>
									<a href="https://github.com/yasithadehigama" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 65px 0px">Yasitha Dehigama</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						<div class="span4">
							<div class="block" style="border-radius: 10px">
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/ruwan.png">
									</div>
									<h4 style="text-align:center;margin:30px 12px 0px;color:white">Intern Student at LSF</h4>
								</div>
									<a href="https://github.com/ruwanopensourse" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 49px 0px">Ruwan Wickramarathna</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						
					</div>
					
					
					<div class "row-fluid" style="margin:30px 30px 30px">
						<div class="span4">
							<div class="block"  style="border-radius: 10px;color:red">
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/Srinath.png">
									</div>
									<h4 style="text-align:center;margin:30px 0px 0px;color:white">Director of Research at WSO2.</h4>
								</div>
									<a href="http://wso2.com/about/team/srinath-perera/" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 50px 0px">Srinath Perera(Advisor)</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						
						<div class="span4">
							<div class="block" style="border-radius: 10px;color:red">
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/Louiqa.jpg">
									</div>
									<h4 style="text-align:center;margin:30px 0px 0px;color:white">Professor, University of Maryland.</h4>
								</div>
									<a href="http://www.umiacs.umd.edu/users/louiqa/" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 40px 0px">Louiqa Raschid(Advisor)</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						<div class="span4">
							<div class="block" style="border-radius: 10px;color:red" >
								<div class="row-fluid" style="background-color:CadetBlue">
									<div class="span4">
										<img src="bootstrap/img/Tharindu.jpg">
									</div>
									<h4 style="text-align:center;margin:30px 12px 0px;color:white">Senior Software Engineer at LSF</h4>
								</div>
									<a href="https://lk.linkedin.com/in/tharindu99" target="_blank">
										<div class="panel-footer">
											<div class="container4">
												<span class="pull-left">
													<h4 style="margin:16px 40px 0px">Tharindu Madushanka</h4>
												</span><br></br> <span></span>
											</div>
										</div>
									</a>
							</div>
						</div>
						
					</div>
					
					
					
					
					
				</div>
            </div>
        </div>
    </body>
</html>

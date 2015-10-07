<!DOCTYPE html>
<html class="no-js">
    
    <head>
        <title>KARSHA-Drawdowns</title>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="assets/styles.css" rel="stylesheet" media="screen">
        <script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <link href="bootstrap/css/c3.css" rel="stylesheet">
<script src="bootstrap/js/graphs.js"></script>
    </head>
    
    <body onload="myFunction('bootstrap/data/aa.json')">
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
                            <li>
								<a href="index.jsp">Home</a>
                            </li> 
							<li>
                            	<a href="about.jsp">About</a>
                            </li>
                        </ul>
                    </div>
                    
                </div>
            </div>
        </div>

        <div class="container-fluid">
			
            <div class="row-fluid">
                <div class="span2" id="sidebar">
                    <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
                        <li>
                            <a href="yearly_analisis.jsp?Q=2004"style="text-align:center">2004 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2005"style="text-align:center">2005 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2006"style="text-align:center">2006 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2007"style="text-align:center">2007 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2008"style="text-align:center">2008 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2009"style="text-align:center">2009 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2010" style="text-align:center">2010 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2011"style="text-align:center">2011 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2012"style="text-align:center">2012 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2013"style="text-align:center">2013 Year</a>
                        </li>
                        <li>
                            <a href="yearly_analisis.jsp?Q=2014"style="text-align:center">2014 Year</a>
                        </li>
                        <li>
                            <a href="summary.jsp"style="text-align:center">Summary</a>
                        </li>
                        
                    </ul>
                </div>  
                
                
                
                
            <div class="span10" style="border:1px solid LightSeaGreen;background-color:white" >
                <div>
					<div class "row-fluid" style="margin:30px 30px 30px">
						<h1>Yearly Analisis</h1>
					</div>
				</div>
				<div>
                		<div class="col-lg-12" style="margin:30px 30px 30px">
                    		<h4 class="page-header">Scatter plot</h4>
                		</div>
            	</div>
                <div class="row">
                 		<div class="col-lg-12" style="margin:30px 30px 30px">
                   			<div id="scatter_plot" ></div>
                    
                		</div>
            	</div>
 
            <!-- /.row -->
            </div>
           
            <!-- /.row -->

        </div>
    <script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="bootstrap/js/graphs.js"></script>
    </body>
</html>
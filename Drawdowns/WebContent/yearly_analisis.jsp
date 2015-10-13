<!DOCTYPE html>
<html class="no-js">
    
    <head>
        <title>KARSHA-Drawdowns</title>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
<<<<<<< HEAD
        <link href="assets/styles.css" rel="stylesheet" media="screen"> 
        <link href="bootstrap/css/c3.css" rel="stylesheet">
		<script src="bootstrap/js/graphs.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>		
=======
        <link href="assets/styles.css" rel="stylesheet" media="screen">
        <script src="vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <link href="bootstrap/css/c3.css" rel="stylesheet">		
		<script src="bootstrap/js/graphs.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		
>>>>>>> branch 'master' of https://github.com/ruwanopensourse/Drawdowns.git
    </head>
    
<<<<<<< HEAD
    <body>
=======
	<body>
>>>>>>> branch 'master' of https://github.com/ruwanopensourse/Drawdowns.git
    
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script>
        	//load the nodes and links arrays
        
        	$.get("IndexSrvlt?Q="+"<%=request.getParameter("Q")%>")
            .error(function () {
                    //alert("there is error while sending data to server");
                	});
        		;
		</script>
		<script>
		//divide data from url to catogories
		$(document).ready(
				function () {
					var url = "IndexSrvlt?Q="+"<%=request.getParameter("Q")%>";
					var i,p;
					var High,High_Medium,Medium,Medium_low,low = [];
					var Arr,PermNo=[],Perm_date = [];
					var H_PermNo=[],HM_PermNo =[],M_PermNo = [],ML_PermNo = [],L_PermNo = [];
					var H_Perm_date=[],HM_Perm_date =[],M_Perm_date = [],ML_Perm_date = [],L_Perm_date = [];
					$.ajax({
		                type: 'GET',
		                url: url,
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
		               	function Perm_Gen(Arr,PermNo,Perm_date)
		               	{
<<<<<<< HEAD
	
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
		               	
		               	var Json_output = JSON.stringify(Ready_output);
		               	console.log(Json_output);
		               	
		               	myFunction('bootstrap/data/aa.json');
		               	myFunction(Json_output);
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
					
				});
				
		                                                                  
		                                                    
		
		
		</script>
		
=======
		               		  			
		        			
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
		               	//console.log(JSON.stringify(Json_output));
		               	var Json_output=JSON.stringify(Ready_output);
		               	console.log(Json_output);
		               	myFunction(Json_output);
		               //	myfunction2(Json_output);
		                },
		                
		                error: function (data,
		                        error) {
		                	console.log(error);
		                },
		                async: false
		            });
					
				});
		</script>


>>>>>>> branch 'master' of https://github.com/ruwanopensourse/Drawdowns.git
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
        </div>
    <script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/c3.js"></script>
	<script src="bootstrap/js/d3.min.js"></script>
	<script src="bootstrap/js/graphs.js"></script>
    </body>
</html>

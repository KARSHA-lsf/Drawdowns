<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<title>Insert title here</title>
</head>
<body>
 Sql : <input type="text" name="sql">
 <input id="sqlsubmit" type="submit" value="Submit">
<div id="allsql"></div>

<script>
$(document).ready(function() {
	var alltext = $('#sql').val();
	console.log(alltext);
	
	var url = "AllSqlGraph?S=SELECT-yrmo,value1-FROM-caaf_drawdowns";
	$.ajax({
		type : 'GET',
		url : url,
		dataType : 'json',
		success : function(data) {
			console.log(data);
		},
		error : function(data, error) {
			console.log(error);
		},
		async : false
	});
	$("#sqlsubmit").click(function(){
        alert(alltext);
    }); 
	
	
});





</script>
</body>
</html>
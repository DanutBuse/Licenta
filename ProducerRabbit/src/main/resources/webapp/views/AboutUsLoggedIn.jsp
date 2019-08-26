<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">

<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

<script type="text/javascript">
		<%@include file="js/jsCode.js" %>
</script>   
<style>
        <%@include file="styles/styles.css" %>
</style>
         
</head>
<body>
	<div class="container">
		<h1 class="text-center">Auto Clinique</h1>	
		<nav class="navbar navbar-light bg-primary">  
			 
			    <div class="navbar-header">  
			      <a class="navbar-brand" href="/AutoClinique/aboutUs">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li ><a href="/AutoClinique/menu">Create Message</a></li>  
			      <li><a href="/AutoClinique/consumeAll">Received Messages</a></li>  
			      <li><a href="/AutoClinique/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/AutoClinique/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
		
		<div class="center-text">
			<h3>Application Info</h3><br><br>
			<div style="margin-left:20;"> Project done by Danut Buse in 2019 for more info about the app contact me on busedanut@yahoo.com<br><br><br><br>
			<a href="https://github.com/DanutBuse/Licenta">Github project link</a>
			</div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">
<style>

<%@include file="styles/styles.css" %>

</style>        
</head>
<body>
	<div class="container">
		<h1 class="text-center">Auto Clinique</h1>	
		<nav class="navbar navbar-light bg-primary">  
			 
			    <div class="navbar-header">  
			      <a class="navbar-brand" href="/ProducerRabbit/">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li><a href="/ProducerRabbit/menu">Create Message</a></li>  
			      <li><a href="/ProducerRabbit/consumeAll">Received Messages</a></li>  
			      <li><a href="/ProducerRabbit/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li><a href="/ProducerRabbit/register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
			      <li id='1' class="active"><a href="/ProducerRabbit/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			   </ul>	
			 
		</nav>
		
		<div class="center-text">
		<h3>Complete fields to login</h3>
		
			<form method = "POST" action = "/ProducerRabbit/login">
			
				 <div class="form-group">
					<label for="Username">Username</label>
					<input type = "text" class="form-control" placeholder="Enter username" name="username">
				 </div>
				 
				 <div class="form-group">	
				 	<label for="InputPassword">Password</label>
					<input type="password" class="form-control" placeholder="Password" name="pass">
				 </div>	
				 
			    <input type = "submit" value = "Login" class="btn btn-primary"/>
		    </form>
		  
		</div>  
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">
    <script src="webjars/jquery/2.1.4/jquery.min.js"></script>
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
			      <li class="active"><a href="/ProducerRabbit/menu">Create Message</a></li>  
			      <li><a href="/ProducerRabbit/consumeAll">Received Messages</a></li>  
			      <li><a href="/ProducerRabbit/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/ProducerRabbit/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
		
		<div class="center-text">
		<h3>Complete to send message</h3>
    <form method = "POST" action = "/ProducerRabbit/sendMessageCustomer">
    	
    	<div class="col-lg-4 col-lg-offset-4">
	       <div class="form-group">
				<label for="marca">Marca</label>
				<input type = "text" class="form-control" placeholder="Marca" name="marca" style="width:300px;margin-left:35px">
		   </div>
	   	   
	   	   <div class="form-group">
				<label for="tip">Tip</label>
				<input type = "text" class="form-control" placeholder="Tip" name="tip" style="width:300px;margin-left:35px">
		   </div>
					 
		   <div class="form-group">
				<label for="Vin">Vin</label>
				<input type = "text" class="form-control" placeholder="Vin" name="vin" style="width:300px;margin-left:35px">
		   </div>
		    
		   <div class="form-group">
				<label for="an">An</label>
				<input type = "text" class="form-control" placeholder="An" name="an" style="width:100px;margin-left:125px">
		   </div>
	   	   
		   <div class="form-group">
				<label for="Descriere">Descriere</label>
				<textarea rows = "5" cols="1000" style="resize:none;margin-left:8px" class="form-control" placeholder="Descriere" name="descriere"></textarea>
		   </div>
	   	   
		    <!--  To:<input id = "receiver" name = "receiver" type = "text" value = ""/></br> -->
		    <input type = "submit" value = "Submit" class="btn btn-primary"/>
	    </div>
    </form>
    	</div>
    </div>
  
</body>

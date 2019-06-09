<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
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
		<h3>Complete to reply</h3>
    	<form method = "POST" action = "/ProducerRabbit/sendMessageSupport">

    		<div class="col-lg-4 col-lg-offset-4">
	       		<div class="form-group">
					<label for="messageId">ID</label>
					<input type = "text" class="form-control" placeholder="Id Message" name="idMesaj" style="margin-left:80px;width:200px">
		   		</div>
	   	   
		   		<div class="form-group">
					<label for="Descriere">Descriere</label>
					<textarea rows = "5" cols="1000" style="resize:none" class="form-control" placeholder="Descriere" name="descriere"></textarea>
		   		</div>
	   	  
	   	  		<button id="b1" class="btn btn-success add-more" style="margin-bottom:10px" type="button">Mai adauga o oferta!</button>
	   	 
				<div class="row" id="multifield1">
					<input type="hidden" name="count" value="1" />
		      	
		      		<div class="column">
		      			<h4>Nume piesa</h4>
			    		<input autocomplete="off" class="input form-control" id="numepiesa1" name="numepiesa1" type="text" placeholder="Nume Piesa" data-items="8" style="margin-left:3px"/>
			    	</div>
			    
			    	<div class="column">
			    		<h4>Producator</h4>
			    		<input autocomplete="off" class="input form-control" id="producator1" name="producator1" style="margin-left:5px" type="text" placeholder="Producator" data-items="8"/>
			    	</div>
			    
			    	<div class="col-xs-4">
			    		<h4>Pret</h4>
			    		<input autocomplete="off" class="input form-control" id="pret1" name="pret1" type="text" style="margin-left:10px" placeholder="Pret" data-items="8"/>
			    	</div>                
		                 
				</div>
				
				<input type = "submit" value = "Submit" class="btn btn-primary" style="margin-top:15px"/>
		 	</div>
		</form>
    </div>
  </div>
  
</body>

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
    
    <script> 
function validate()                                    
{ 
    var marca = document.forms["RegForm"]["marca"];      
    var tip = document.forms["RegForm"]["tip"];
    var an = document.forms["RegForm"]["an"];      
    var descriere = document.forms["RegForm"]["descriere"];
    var vin = document.forms["RegForm"]["vin"];
    
    if (marca.value == "")                                  
    { 
    	if (document.getElementById("marcaErMsg") !=null) {
    		document.getElementById("marcaErMsg").remove();
    	}
    	
    	marca.className = "form-control is-invalid";
        $('[name="marca"]').before("<small id='marcaErMsg'>Empty Brand field</small>");
        marca.focus(); 
        return false; 
        
    } else{ 
    	marca.className = "form-control is-valid";
    	marca.focus();  
    } 
    
   
   
    if (tip.value == "")                                  
    { 
    	if (document.getElementById("tipErMsg") !=null) {
    		document.getElementById("tipErMsg").remove();
    	}
    	
    	tip.className = "form-control is-invalid";
        $('[name="tip"]').before("<small id='tipErMsg'>Empty Type field</small>");
        tip.focus(); 
        return false; 
        
    } else{ 
    	tip.className = "form-control is-valid";
    	tip.focus();  
    } 
   
    if (vin.value == "")                                  
    { 
    	if (document.getElementById("vinErMsg") !=null) {
    		document.getElementById("vinErMsg").remove();
    	}
    	
    	vin.className = "form-control is-invalid";
        $('[name="vin"]').before("<small id='vinErMsg'>Empty Brand field</small>");
        vin.focus(); 
        return false; 
        
    } else{ 
    	vin.className = "form-control is-valid";
    	vin.focus();  
    } 
   
    
    if (an.value < 1980)                                  
    { 
    	if (document.getElementById("anErMsg") !=null) {
    		document.getElementById("anErMsg").remove();
    	}
    	
    	an.className = "form-control is-invalid";
        $('[name="an"]').before("<small id='anErMsg'>Empty Fabrication Year field</small>");
        an.focus(); 
        return false; 
        
    } else{ 
    	an.className = "form-control is-valid";
    	an.focus();  
    } 
    
    if (descriere.value == "")                                  
    { 
    	if (document.getElementById("descriereErMsg") !=null) {
    		document.getElementById("descriereErMsg").remove();
    	}
    	
    	descriere.className = "form-control is-invalid";
        $('[name="descriere"]').before("<small id='descriereErMsg'>Empty Description field</small>");
        descriere.focus(); 
        return false; 
        
    } else{ 
    	descriere.className = "form-control is-valid";
    	descriere.focus();  
    } 
    
    e.preventDefault();
    return true; 
}</script> 
    
</head>
<body>
	<div class="container">
		<h1 class="text-center">Auto Clinique</h1>	
		<nav class="navbar navbar-light bg-primary">  
			 
			    <div class="navbar-header">  
			      <a class="navbar-brand" href="/AutoClinique/">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li class="active"><a href="/AutoClinique/menu">Create Message</a></li>  
			      <li><a href="/AutoClinique/consumeAll">Received Messages</a></li>  
			      <li><a href="/AutoClinique/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/AutoClinique/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
		
		<div class="center-text">
		<h3>Complete to send message</h3>
    <form method = "POST" action = "/AutoClinique/sendMessageCustomer" name = "RegForm" onsubmit="return validate()">
    	
    	<div class="col-lg-4 col-lg-offset-4">
	       <div class="form-group">
				<label for="marca">Brand</label>
				<input type = "text" class="form-control" placeholder="Brand" name="marca" style="width:300px;margin-left:35px">
		   </div>
	   	   
	   	   <div class="form-group">
				<label for="tip">Type</label>
				<input type = "text" class="form-control" placeholder="Type" name="tip" style="width:300px;margin-left:35px">
		   </div>
					 
		   <div class="form-group">
				<label for="Vin">Vin</label>
				<input type = "text" class="form-control" placeholder="Vin" name="vin" style="width:300px;margin-left:35px">
		   </div>
		    
		   <div class="form-group">
				<label for="an">Fabrication year</label>
				<input type = "text" class="form-control" placeholder="Fabrication Year" name="an" style="width:100px;margin-left:125px">
		   </div>
	   	   
		   <div class="form-group">
				<label for="Descriere">Description</label>
				<textarea rows = "5" cols="1000" style="resize:none;margin-left:8px" class="form-control" placeholder="Description" name="descriere"></textarea>
		   </div>
	   	   
		    <!--  To:<input id = "receiver" name = "receiver" type = "text" value = ""/></br> -->
		    <input type = "submit" value = "Submit" class="btn btn-primary"/>
	    </div>
    </form>
    	</div>
    </div>
  
</body>

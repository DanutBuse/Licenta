<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here </title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet"/>

<style>
	<%@include file="styles/styles.css" %>
</style>

<script> 
function validate()                                    
{ 
    var name = document.forms["RegForm"]["username"];      
    var password = document.forms["RegForm"]["pass"];
    
    if (name.value == "")                                  
    { 
    	if (document.getElementById("userNamee") !=null) {
    		document.getElementById("userNamee").remove();
    	}
    	
        name.className = "form-control is-invalid";
        $('[name="username"]').before("<small id='userNamee'>Empty Username field</small>");
        name.focus(); 
        return false; 
        
    } else{ 
        name.className = "form-control is-valid";
        name.focus();  
    } 
   
    if (password.value.length < 6)                                  
    { 
    	if (document.getElementById("passs") !=null) {
    		document.getElementById("passs").remove();
    	}
    	
    	password.className = "form-control is-invalid";
        $('[name="pass"]').before("<small id='passs'>Password less than 6 characters</small>");
        password.focus(); 
        return false; 
        
    } else{ 
    	password.className = "form-control is-valid";
    	password.focus();  
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
			  
				<ul class="nav navbar-nav navbar-right">
			      <li class="active"><a href="/AutoClinique/register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
			      <li id='1'><a href="/AutoClinique/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			   </ul>	
			 
		</nav>
		<div class="center-text">
		<h3>Complete fields to register</h3>
		<form method = "POST" action = "/AutoClinique/register" name = "RegForm" onsubmit="return validate()">
		
			 <div class="form-group">
				<label for="Username">Username</label>
				<input type = "text" class="form-control" id="exampleInputEmail1" placeholder="Enter username" name="username">
			 </div>
			 
			 <div class="form-group">	
			 	<label for="InputPassword">Password</label>
				<input type="password" class="form-control" id="InputPassword" placeholder="Password" name="pass">
			 </div>
			 
			 <div class="form-check">
		        <input type="radio" name="typeName" id="customer"  class="form-check-input" value="customer" checked="checked"/>
		     	<label class="form-check-label" for="customer">Customer</label>
		     	
		       	<input  type="radio" name="typeName"  id="support" value="support" class="form-check-input"/>	
		       	<label class="form-check-label" for="support">Support</label>
		     </div>	
			 
			 <input type = "submit" class="btn btn-primary" value = "Register"/><br>
		   
	    </form>  
	    </div>
	</div> 	
</body>
</html>
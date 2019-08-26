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
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js">
        </script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js">
        </script>
<script type="text/javascript">
	<%@include file="js/jsCode.js" %>
</script>    
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
			      <a class="navbar-brand" href="/AutoClinique/aboutUs">About us</a>  
			    </div>  
				<ul class="nav navbar-nav navbar-right">
			      <li><a href="/AutoClinique/register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
			      <li id='1' class="active"><a href="/AutoClinique/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			   </ul>	
			 
		</nav>
		
		<div class="center-text">
		<h3>Complete fields to login</h3>
		
			<form method = "POST" action = "/AutoClinique/login" onsubmit="return validate()" name="RegForm">
			
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
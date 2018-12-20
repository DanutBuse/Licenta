<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method = "POST" action = "/ProducerRabbit/register">
		<input type = "text" value = "Username" name="username"><br>
		<input type = "text" value = "Password" name="pass"><br>
		<input type = "text" value = "Type" name="tip"><br>
	    <input type = "submit" value = "Register"/><br>
    </form>
    <form method = "GET" action = "/ProducerRabbit/login">
    	Go To Login Page <br>
	    <input type = "submit" value = "Login"/><br>
    </form>
    	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method = "POST" action = "/ProducerRabbit/add/user">
		<input type = "text" value = "Username" name="username"><br>
		<input type = "text" value = "Password" name="pass"><br>
		<input type = "text" value = "Type" name="tip"><br>
	    <input type = "submit" value = "Add"/><br>
    </form>
    
    <form method = "GET" action = "/ProducerRabbit/produce">
    	<input type = "submit" value = "Produce"/>
    </form>
    	
</body>
</html>
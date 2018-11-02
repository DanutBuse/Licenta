<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Received ${message}</h1>
	<form method = "GET" action = "/ProducerRabbit/produce">
	    <input type = "submit" value = "produceAgain"/>
    </form>
</body>
</html>
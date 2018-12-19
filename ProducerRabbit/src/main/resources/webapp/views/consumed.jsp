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
	<form action = "localhost:8080/ProducerRabbit/produceInfo">
	    <input type = "submit" value = "Back To Menu"/>
    </form>
</body>
</html>
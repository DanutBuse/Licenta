<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:forEach var="message" items="${messages}" varStatus="counter">
		<h1>From ${message.sender.username} Received ${message.data}</h1><br>	
	 </c:forEach>
	<form method = "GET" action = "/ProducerRabbit/menu">
	    <input type = "submit" value = "Back to menu"/>
    </form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!--  <%@include file="navbar.jsp" %>-->
	<h1>From ${message.sender.username} Received ${message.data}</h1>
	<form method = "GET" action = "/AutoClinique/menu">
	    <input type = "submit" value = "Back to menu"/>
    </form>
</body>
</html>
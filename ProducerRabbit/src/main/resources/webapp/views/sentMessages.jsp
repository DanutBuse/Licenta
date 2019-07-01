<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">

<script src="webjars/jquery/2.1.4/jquery.min.js"></script>

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
			      <a class="navbar-brand" href="/AutoClinique/">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li ><a href="/AutoClinique/menu">Create Message</a></li>  
			      <li><a href="/AutoClinique/consumeAll">Received Messages</a></li>  
			      <li class="active"><a href="/AutoClinique/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/AutoClinique/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
		
		<h3>Sent Messages</h3>
		<c:if test="${empty messages}">
			    	<h2>No messages that were sent werent received</h2>
		</c:if>
	
		<c:if test="${not empty messages}">
		<table class ="table table-striped table-bordered" id="tabel1">
			<thead class="thead-dark">
				<tr>
					<th onclick="sortTable(0)">Ticket Number</th>
					<th onclick="sortTable(1)">To</th>
					<th onclick="sortTable(2)">Brand</th>
					<th onclick="sortTable(3)">Type</th>
					<th onclick="sortTable(4)">Vin</th>
					<th onclick="sortTable(5)">Fabrication Year</th>
					<th onclick="sortTable(6)">Description</th>
					<th onclick="sortTable(7)">Sent Date</th>
				</tr>	
			</thead>		
		    <tbody class="table-hover">
			    <c:forEach var="message" items="${messages}" varStatus="counter">
					<tr>
						<td>${message.id}</td>
						<td>${message.receiver.username}</td>
						<td>${message.masina.marca}</td>
						<td>${message.masina.tip}</td>
						<td>${message.masina.vin}</td>
						<td>${message.masina.an}</td>
						<td>${message.descriere}</td>	
						<td>${message.sentDate}
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
	</div>
</body>
</html>
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
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
			<%@include file="js/jsCode.js" %>
	</script> 
	
	<style>
	        <%@include file="styles/styles.css" %>
	</style>
	   <script>
						$(function() {
	
							  $("#myModal${loop.index}").dialog({
							     autoOpen: false,
							     modal: true
							   });
	
							  $("#myButton${loop.index}").on("click", function(e) {
							      e.preventDefault();
							      $("#myModal${loop.index}").dialog("open");
							  });
								
							});
						
						$('input[type="checkbox"]').each(function(el) {
						    var hidden = $('<input type="hidden" />');
						    hidden.name = el.name;
						    el.after(hidden);
						    el.on("change", function(el) {
						       hidden.value = el.checked ? "true" : "false";
						    });
						});
		</script> 
</head>
<body>
	<div class="container">
		<h1 class="text-center">Auto Clinique</h1>	
		<nav class="navbar navbar-light bg-primary">  
			 
			    <div class="navbar-header">  
			      <a class="navbar-brand" href="/ProducerRabbit/">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li ><a href="/ProducerRabbit/menu">Create Message</a></li>  
			      <li class="active"><a href="/ProducerRabbit/consumeAll">Received Messages</a></li>  
			      <li><a href="/ProducerRabbit/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/ProducerRabbit/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
		
		<h3>Received Messages</h3>
		<c:if test="${empty messages}">
			    	<h2>No messages were received</h2>
		</c:if>
		<c:if test="${not empty messages}">
		<table class ="table table-striped table-bordered" id="tabel">
			<thead class="thead-dark">
				<tr>
					<th onclick="sortTable(0)">Numar Tichet</th>
					<th onclick="sortTable(1)">From</th>
					<th onclick="sortTable(2)">Marca</th>
					<th onclick="sortTable(3)">Tip</th>
					<th onclick="sortTable(4)">Vin</th>
					<th onclick="sortTable(5)">An</th>
					<th onclick="sortTable(7)">Descriere</th>
					<th onclick="sortTable(8)">Received Date</th>
					<th>Info</th>
				</tr>
			</thead>		
			
			
		    <tbody class="table-hover">
			    <c:forEach var="message" items="${messages}" varStatus="loop">
					<tr id="tr${loop.index}">
						<td>${message.id}</td>
						<td>${message.sender.username}</td>
						<td>${message.masina.marca}</td>
						<td>${message.masina.tip}</td>
						<td>${message.masina.vin}</td>
						<td>${message.masina.an}</td>
						<td>${message.descriere}</td>	
						<td>${message.receivedDate}</td>
						<td>
							<button id="myButton${loop.index}" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal${loop.index}">Info${loop.index + 1}</button>
							<div id="myModal${loop.index}" class="modal fade" role="dialog">
							  <div class="modal-dialog modal-lg">
							
							    <!-- Modal content-->
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">Informatii mesaj</h4>
							      </div>
							      <div class="modal-body">
							      	<form method = "POST" action = "/ProducerRabbit/sendReplyFromCustomer">
							      	 <c:if test="${not empty message.oferte}">
							      	 	<label for="tabel2">Oferte primite</label>
								       	<table class ="table table-bordered" id="tabel2">
											<thead class="thead-dark">
												<tr>
													<th onclick="sortTable(0)">Cod</th>
													<th onclick="sortTable(1)">Producator</th>
													<th onclick="sortTable(2)">Nume Piesa</th>
													<th onclick="sortTable(3)">Pret</th>
													<th>Comanda</th>
												</tr>
											</thead>
											
											 <tbody class="table-hover">
											    <c:forEach var="oferta" items="${message.oferte}" varStatus="counter">
													<tr id="trMOD${counter.index}">
														<td>${oferta.id}</td>
														<td>${oferta.producator}</td>
														<td>${oferta.numePiesa}</td>
														<td>${oferta.pret}</td>
													    <td>
													    	  <div class="form-check">
															    <input type="checkbox" class="form-check-input" id="comandat${oferta.id}" name="comandat${oferta.id}">
															  </div>
														</td>
													</tr>
												</c:forEach> 
											</tbody>		
										</table>	
							    	</c:if>
							    	  <input type="hidden" id="mesID" name="mesajID" value="${message.id}">
							    	  
								      <c:if test="${not empty message.conversatie}">
								    	<label for="DescriereInitiala">Conversatie curenta</label>
								    	<textarea readonly name='descriereInitiala' rows = "8" cols="1000" style="resize:none;margin-left:8px" class="form-control">${message.conversatie}</textarea>
							    	  </c:if>
							    	  
								      <label for="Descriere">Trimite raspuns</label>
									  <textarea rows = "2" cols="1000" style="resize:none;margin-left:8px" class="form-control" placeholder="Descriere" name="descriereAditionala"></textarea>
								      
								      <button type="submit" class="btn btn-danger" style="margin-top:10px">Reply</button>
							      	</form>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							      </div>
							    </div>
							
							  </div>
							</div>
						</td>	
					</tr>
		
					
				</c:forEach>	
			</tbody>
		</table>
		</c:if>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
        rel="stylesheet">
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
   <script type="text/javascript">
		<%@include file="js/jsCode.js" %>
		
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
		
	</script> 
	
	<script>
	function validate(index) {
		var descriereAditionala = document.forms["RegForm" + index]["descriereAditionala" + index];

		if (descriereAditionala.value == "") {
			if (document.getElementById("descriereAditionalaErMsg" + index) != null) {
				document.getElementById("descriereAditionalaErMsg" + index).remove();
			}

			descriereAditionala.className = "form-control is-invalid";
			$('[name="descriereAditionala' + index + '"]').before(
							"<small id='descriereAditionalaErMsg"+index+"'>Empty Message field</small>");
			descriereAditionala.focus();
			return false;

		} else {
			descriereAditionala.className = "form-control is-valid";
			descriereAditionala.focus();
		}

		e.preventDefault();
		return true;
	}
</script>
	
	<script> 
function validateUserField()                                    
{ 
    var name = document.forms["RegForm"]["customerNameInput"];      
   
    
    if (name.value == "")                                  
    { 
    	if (document.getElementById("userNamee") !=null) {
    		document.getElementById("userNamee").remove();
    	}
    	
        name.className = "form-control is-invalid";
        $('[name="customerNameInput"]').before("<small id='userNamee'>Empty Username field</small>");
        name.focus(); 
        return false; 
        
    } else{ 
        name.className = "form-control is-valid";
        name.focus();  
    } 
   
    e.preventDefault();
    return true; 
}</script> 
	
	<style>
	        <%@include file="styles/styles.css" %>
	</style>
</head>
<body>
	<div class="container">
		<h1 class="text-center">Auto Clinique</h1>	
		<nav class="navbar navbar-light bg-primary">  
			 
			    <div class="navbar-header">  
			      <a class="navbar-brand" href="/AutoClinique/aboutUs">About us</a>  
			    </div>  
			    <ul class="nav navbar-nav">  
			      <li class="active"><a href="/AutoClinique/menu">Create Message</a></li>  
			      <li><a href="/AutoClinique/consumeAll">Received Messages</a></li>  
			      <li><a href="/AutoClinique/sentMessages">Sent Messages</a></li>      
			    </ul>  
				<ul class="nav navbar-nav navbar-right">
			      <li id='2'><a href="/AutoClinique/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			   </ul>	
			 
		</nav>
	<div class="center-text">
	
		<ul class="nav nav-tabs">
		  <li class="nav-item">
		    <a class="activeNav" href="/AutoClinique/menu">Send Offer</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="/AutoClinique/broadcast">BroadCast message</a>
		  </li>
		</ul>
		
		<form method = "GET" action = "/AutoClinique/getCustomerMessages" onsubmit="return validateUserField()" name="RegForm">
			<label for="CustomerName">Customer name:</label>
			<input type = "text" class="form-control" placeholder="Customer Name" name="customerNameInput" id="customerNameInput" style="width:300px;margin-left:10px">
	    
	    	<input type = "submit" value = "Submit" class="btn btn-primary" id="displayCustomerMessages" style="margin-top:10px"/>
	    </form>
	    
	    <h3>Received Messages</h3>
		<c:if test="${empty messages}">
			    	<h2>No messages were received</h2>
		</c:if>
		<c:if test="${not empty messages}">
		<table class ="table table-bordered" id="tabel">
			<thead class="thead-dark">
				<tr>
					<th onclick="sortTable(0)">Ticket Number</th>
					<th onclick="sortTable(1)">From</th>
					<th onclick="sortTable(2)">Brand</th>
					<th onclick="sortTable(3)">Type</th>
					<th onclick="sortTable(4)">Vin</th>
					<th onclick="sortTable(5)">Fabrication Year</th>
					<th onclick="sortTable(7)">Description</th>
					<th onclick="sortTable(8)">Received Date</th>
					<th>Info</th>
					<th>Delete</th>
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
							  <div class="modal-dialog mw-100 w-75">
							
							    <!-- Modal content-->
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							        <h4 class="modal-title">Informatii mesaj</h4>
							      </div>
							      <div class="modal-body">
							      	<c:if test="${not empty message.oferte}">
							      		<label for="tabel2">Oferte deja trimise</label>
								       	<table class ="table table-striped table-bordered" id="tabel2">
											<thead class="thead-dark">
												<tr>
													<th onclick="sortTable(0)">Part Number</th>
													<th onclick="sortTable(1)">Producer</th>
													<th onclick="sortTable(2)">Part Name</th>
													<th onclick="sortTable(3)">Price (euro)</th>
												</tr>
											</thead>
											
											 <tbody class="table-hover">
											    <c:forEach var="oferta" items="${message.oferte}" varStatus="counter">
													<tr id="trMOD${counter.index}">
														<td>${oferta.id}</td>
														<td>${oferta.producator}</td>
														<td>${oferta.numePiesa}</td>
														<td>${oferta.pret}</td>
													</tr>
												</c:forEach> 
											</tbody>		
										</table>	
							    	</c:if>
							    	<c:if test="${not empty message.conversatie}">
								    	<label for="DescriereInitiala">Conversation</label>
								    	<textarea readonly name='descriereInitiala' rows = "8" cols="1000" style="resize:none;margin-left:8px" class="form-control">${message.conversatie}</textarea>
							    	</c:if>
							      	<form method = "POST" action = "/AutoClinique/sendReplyFromSupport" name="RegForm${loop.index}"
													onsubmit="return validate('${loop.index}');">
							    	  
								      <label for="Descriere">Send Message</label>
									  <textarea rows = "2" cols="1000" style="resize:none;margin-left:8px" class="form-control" placeholder="Description" name="descriereAditionala"></textarea>
								      
								      <button id="addMore${loop.index}" class="btn btn-success add-more" style="margin-bottom:10px;margin-top:10px" type="button"
								      				onclick="addMoreOffers('${loop.index}');">Add one more offer!</button>
								      <div class="row" id="multifield${loop.index}fieldNum0">
											<input type="hidden" name="count" value="1" />
								      	
								      		<div class="column">
								      			<h4>Part Name</h4>
									    		<input autocomplete="off" class="input form-control" id="numepiesa${loopIndex}fieldNum0"+ name="numepiesa0" type="text" placeholder="Part Name" data-items="8" style="margin-left:3px"/>
									    	</div>
									    
									    	<div class="column">
									    		<h4>Producer</h4>
									    		<input autocomplete="off" class="input form-control" id="producator${loopIndex}fieldNum0" name="producator0" style="margin-left:5px" type="text" placeholder="Producer" data-items="8"/>
									    	</div>
									    
									    	<div class="col-xs-4">
									    		<h4>Price (euro)</h4>
									    		<input autocomplete="off" class="input form-control" id="pret${loopIndex}fieldNum0" name="pret0" type="text" style="margin-left:10px" placeholder="Price" data-items="8"/>
									    	</div>                
								                 
									  </div>
									  <br>
									  <input type="hidden" id="mesID" name="mesajID" value="${message.id}">
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
						<td><a href="/AutoClinique/delete/sent/message/${message.id}/"><span class="glyphicon glyphicon-trash"></span></a></td>		
					</tr>
		
					
				</c:forEach>	
			</tbody>
		</table>
		</c:if>
    </div>
  </div>
  
</body>

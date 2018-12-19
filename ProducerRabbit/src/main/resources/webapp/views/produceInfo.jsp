<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
    <form method = "POST" action = "/ProducerRabbit/send">
    	SEND  FORM </br>
	    Data:<input id = "name" name = "name" type = "text" value = ""/></br>
	    To:<input id = "receiver" name = "receiver" type = "text" value = ""/></br>
	    <input type = "submit" value = "Submit"/>
    </form>
    
    <form method = "POST" action = "/ProducerRabbit/consume">
    	</br></br>
    	See what u received 
    	</br>
    	<input type = "submit" value = "Submit"/>
    </form>
</body>

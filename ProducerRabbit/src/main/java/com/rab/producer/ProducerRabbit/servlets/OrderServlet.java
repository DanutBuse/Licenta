package com.rab.producer.ProducerRabbit.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rab.producer.ProducerRabbit.entity.MessageEntity;

@WebServlet(
		name = "AnnotationExample",
		description = "Example Servlet Using Annotations",
		urlPatterns = {"/order"}
)
public class OrderServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		
		List<MessageEntity> messages = (List<MessageEntity>) req.getAttribute("messages");
		String orderBy = req.getParameter("orderBy");
		
		orderMessages(messages, orderBy);
		
	}

	private void orderMessages(List<MessageEntity> messages, String orderBy) {
		
		switch(orderBy) {
			case "marca":
				messages.sort( (MessageEntity m1, MessageEntity m2) -> {
					
					return m1.getMasina().getMarca().compareTo(m2.getMasina().getMarca());
					
				});
		
			case "tip":
				messages.sort( (MessageEntity m1, MessageEntity m2) -> {
					
					return m1.getMasina().getTip().compareTo(m2.getMasina().getTip());
					
				});
				
			case "an":
				messages.sort( (MessageEntity m1, MessageEntity m2) -> {
					
					return m1.getMasina().getAn().compareTo(m2.getMasina().getAn());
					
				});
				
		}
	}
}

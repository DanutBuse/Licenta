package com.rab.licenta.AutoClinique.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rab.licenta.AutoClinique.CarMapper;
import com.rab.licenta.AutoClinique.OfertaDtoMapper;
import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.dto.OfertaDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.OfertaEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

@Service
public class ConsumerService {
	
	public ConsumerService() {
		
	}
	
	public boolean isQueueEmpty(String queueName) {
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        long count = 0;
        
		try {
			
			connection = factory.newConnection();
			
	        Channel channel = connection.createChannel();
	        
	        count = channel.messageCount(queueName);
	        
	        channel.close();
	        connection.close();
        
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		if(count == 0) {
			return true;
		}
		else
			return false;
	}
	
	public void declareQueue(String queueName,String exchangeName,String rountingKey) {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        
		try {
			
		connection = factory.newConnection();
		
        Channel channel = connection.createChannel();
        
        channel.exchangeDeclare(exchangeName, "direct",true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, rountingKey);
        
        channel.close();
        connection.close();
        
	} catch (IOException | TimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
		
	}

	
	 public List<MessageEntity> receivedMessagesCustomer(User receiver){
	    	ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        Connection connection;
	        
	        List<MessageEntity> messages = new ArrayList<>();
	        String queueName = receiver.getQueue().getQueueName();
			try {
				
			connection = factory.newConnection();
			
	        Channel channel = connection.createChannel();
	        
	        MessageEntity currentMessage = new MessageEntity(new CarEntity(), new User(), receiver,null,"",true,true);
	        
	        GetResponse response = channel.basicGet(queueName, true);
	        
	        while(response != null) {
	        	
	        	setMessagePropertiesCustomer(response,currentMessage);
	            
	            messages.add(currentMessage);
	            
	            currentMessage = new MessageEntity(new CarEntity(), new User(), receiver,null,"",true,true);
	            
	            response = channel.basicGet(queueName, true);
	        }
	        
	        channel.close();
	        connection.close();
	        
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return messages;
	    }
	 

	 private void setMessagePropertiesCustomer(GetResponse response, MessageEntity currentMessage) {
 	
 	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	
 	Gson gson = new Gson();
 	
 	MessageWrapperDTO mesaj = gson.fromJson(new String(response.getBody()), MessageWrapperDTO.class);
 	
 	currentMessage.getSender().setUsername(mesaj.getSenderName());//trb pus user mai apoi
 	
 	try {
 	
 		currentMessage.getMasina().setVin(mesaj.getIdCar());
 		currentMessage.setDescriere(mesaj.getDescriere());
 		currentMessage.setConversatie(mesaj.getConversatie());
		currentMessage.setSentDate(format.parse( String.valueOf(response.getProps().getHeaders().get("sentDate"))));
			
		Date date = format.parse(format.format(new Date()));  
        Timestamp x = new Timestamp(date.getTime()); 
        
		currentMessage.setReceivedDate(x);
		
		List<OfertaEntity> oferte = new ArrayList<>();
		for(int i = 0; i < mesaj.getListaOferte().size(); i++) {
			OfertaEntity ent = OfertaDtoMapper.fromDTO(mesaj.getListaOferte().get(i));
			ent.setMesaj(currentMessage);
			oferte.add(ent);
		}
		currentMessage.setOferte(oferte);
		if(!mesaj.getIdMesaj().isEmpty())
			currentMessage.setId( Integer.parseInt(mesaj.getIdMesaj()));
		else
			currentMessage.setId(-1);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
     
 }
	public void deleteQueue(String queueName) {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        
		try {
			
		connection = factory.newConnection();
        Channel channel = connection.createChannel();
 
        channel.queueDelete(queueName);
        
        channel.close();
        connection.close();
        
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<MessageEntity> receivedMessagesSupport(User receiver) {
	
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
        
        List<MessageEntity> messages = new ArrayList<>();
        String queueName = receiver.getQueue().getQueueName();
		try {
			
		connection = factory.newConnection();
		
        Channel channel = connection.createChannel();
        
        MessageEntity currentMessage = new MessageEntity(new CarEntity(), new User(), receiver,null,"",true,true);
        
        GetResponse response = channel.basicGet(queueName, true);
        
        while(response != null) {
        	
        	setMessagePropertiesSupport(response,currentMessage);
            
            messages.add(currentMessage);
            
            currentMessage = new MessageEntity(new CarEntity(), new User(), receiver,null,"",true,true);
            
            response = channel.basicGet(queueName, true);
        }
        
        channel.close();
        connection.close();
        
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messages;
	}

	private void setMessagePropertiesSupport(GetResponse response, MessageEntity currentMessage) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	
		Gson gson = new Gson();
	 	
	 	MessageWrapperDTO mesaj = gson.fromJson(new String(response.getBody()), MessageWrapperDTO.class);
	 	
	 	currentMessage.getSender().setUsername(mesaj.getSenderName());//trb pus user mai apoi
	 	
	 	try {
	 		
	 		CarEntity car = CarMapper.toEntity(mesaj.getCar());
    		car.setClient(currentMessage.getSender()); 
    		
    		currentMessage.setMasina(car);
    		currentMessage.setConversatie(mesaj.getConversatie());
	 		currentMessage.setDescriere(mesaj.getDescriere());
			currentMessage.setSentDate(format.parse( String.valueOf(response.getProps().getHeaders().get("sentDate"))));
				
			Date date = format.parse(format.format(new Date()));  
	        Timestamp x = new Timestamp(date.getTime()); 
	        
			currentMessage.setReceivedDate(x);
			
			if(!mesaj.getIdMesaj().equals(""))
				currentMessage.setId( Integer.parseInt(mesaj.getIdMesaj()));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}

package com.rab.licenta.AutoClinique.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rab.licenta.AutoClinique.CarMapper;
import com.rab.licenta.AutoClinique.dto.CarDTO;
import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.dto.OfertaDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class ProducerService {
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	public ProducerService() {
		
	}
	
	public void produceMsg(String msg, String EXCHANGE_NAME, String ROUTING_KEY, String sender){
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MessageProperties prop = new MessageProperties();
		prop.setHeader("sender", sender);
		prop.setHeader("sentDate", format.format(new Date()));
		
		Message message = new Message(msg.getBytes(Charset.forName("UTF-8")),prop);
		
		amqpTemplate.send(EXCHANGE_NAME, ROUTING_KEY, message);
		
		System.out.println("Send msg = " + msg);
		
	}

	public String getLowestNumberMessagesQueue(List<String> queues) {
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        Connection connection;
        try {
        	connection = factory.newConnection();
    		
            Channel channel = connection.createChannel();
            
            
            String minQueue = queues.stream().min( (q1, q2) -> {
					try {
						if(channel.messageCount(q1) < channel.messageCount(q2))
							return -1;
					
					
						if(channel.messageCount(q1) == channel.messageCount(q2))
							return 0;
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						return 1;
					}	
            ).get();
            
	        channel.close();
	        connection.close();
	        return minQueue;
        
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public void sendMessageInfo(CarEntity car, String EXCHANGE_NAME, String ROUTING_KEY, String sender,
								String des, String idMesaj, String conversatie) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MessageProperties prop = new MessageProperties();
		prop.setHeader("sentDate", format.format(new Date()));
		
		CarDTO carDTO = CarMapper.toDTO(car);
		MessageWrapperDTO mesaj = new MessageWrapperDTO(null, idMesaj, carDTO, car.getVin(), des, sender, conversatie);
		
		Gson gson = new Gson();
		
		Message message = new Message(gson.toJson(mesaj).getBytes(),prop);
		
		amqpTemplate.send(EXCHANGE_NAME, ROUTING_KEY, message);
		
	}

	public void sendMessageInfo(String marca, String tip, String vin, String an, String descriere,
			String exchangeName, String routingKey, String sender) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MessageProperties prop = new MessageProperties();
		prop.setHeader("sentDate", format.format(new Date()));
		
		CarDTO carDTO = new CarDTO(marca,tip,vin,Integer.parseInt(an));
		MessageWrapperDTO mesaj = new MessageWrapperDTO(null, "", carDTO, "", descriere, sender, "");
		
		Gson gson = new Gson();
		
		Message message = new Message(gson.toJson(mesaj).getBytes(),prop);
		
		amqpTemplate.send(exchangeName, routingKey, message);
		
	}
	
	public void sendMessageSupportInfo(String marca, String tip, String vin, String an, String descriere,
			String exchangeName, String routingKey, String sender, ArrayList<OfertaDTO> listaOferte, String idMesaj) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MessageProperties prop = new MessageProperties();
		prop.setHeader("sentDate", format.format(new Date()));
		
		CarDTO carDTO = new CarDTO(marca,tip,vin,Integer.parseInt(an));
		MessageWrapperDTO mesaj = new MessageWrapperDTO(listaOferte, idMesaj, carDTO, vin, descriere, sender, "");
		
		Gson gson = new Gson();
		
		Message message = new Message(gson.toJson(mesaj).getBytes(),prop);
		
		amqpTemplate.send(exchangeName, routingKey, message);
		
	}

	public void sendMessageFromSupport(ArrayList<OfertaDTO> listaOferte, String idMesaj, String conversatie,
										String exchangeName, String routingKey, String sender, String descriere, String idMasina) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MessageProperties prop = new MessageProperties();
		
		prop.setHeader("sentDate", format.format(new Date()));
		
		MessageWrapperDTO mesaj = new MessageWrapperDTO(listaOferte, idMesaj, null, idMasina, descriere, sender, conversatie);
		
		Gson gson = new Gson();
		
		Message message = new Message(gson.toJson(mesaj).getBytes(), prop);
		
		amqpTemplate.send(exchangeName, routingKey, message);
	}
}


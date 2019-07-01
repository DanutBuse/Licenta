package com.rab.licenta.AutoClinique.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rab.licenta.AutoClinique.CarMapper;
import com.rab.licenta.AutoClinique.OfferDtoMapper;
import com.rab.licenta.AutoClinique.constants.WebConstants;
import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.OfferEntity;
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

		if (count == 0) {
			return true;
		} else
			return false;
	}

	public void declareQueue(String queueName, String exchangeName, String rountingKey) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;

		try {

			connection = factory.newConnection();
			Channel channel = connection.createChannel();
			
			channel.exchangeDeclare(exchangeName, "direct", true);
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, exchangeName, rountingKey);

			channel.close();
			connection.close();

		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<MessageEntity> receivedMessages(User receiver) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(WebConstants.HOST);
		Connection connection = null;
		Channel channel = null;
		
		List<MessageEntity> messages = new ArrayList<>();
		String queueName = receiver.getQueue().getQueueName();
		try {

			connection = factory.newConnection();
			channel = connection.createChannel();
			MessageEntity currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true, true);
			long count = channel.messageCount(queueName);
			
			if(count > 0) {
				GetResponse response = channel.basicGet(queueName, true);
				
				
				if(count > WebConstants.MAX_NUMBER_MESSAGES_CONSUME)
					for(int i = 0; i < WebConstants.MAX_NUMBER_MESSAGES_CONSUME - 1; i++){
						if(receiver.getTipUtilizator().equals(WebConstants.CUSTOMER))
							setMessagePropertiesCustomer(response, currentMessage);
						else
							setMessagePropertiesSupport(response, currentMessage);
						
						messages.add(currentMessage);
						currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true, true);
						response = channel.basicGet(queueName, true);
					}
				else
					while (response != null) {
						if(receiver.getTipUtilizator().equals(WebConstants.CUSTOMER))
							setMessagePropertiesCustomer(response, currentMessage);
						else
							setMessagePropertiesSupport(response, currentMessage);
						
						messages.add(currentMessage);
						currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true, true);
						response = channel.basicGet(queueName, true);
					}
			}

		} catch (IOException | TimeoutException e) {
			System.out.println("Invalid Connection, Channel or timeout response!");
			e.printStackTrace();
		}finally {
			try {
				if(channel !=null)
					channel.close();
				if(connection !=null)
					connection.close();
			} 
			catch (Exception e) {//ignored
			}
		}
			
		return messages;
	}

	private GetResponse fillListItem(List<MessageEntity> messages, User receiver, 
						GetResponse response, MessageEntity currentMessage,Channel channel, String queueName) throws IOException {
		
		if(receiver.getTipUtilizator().equals(WebConstants.CUSTOMER))
			setMessagePropertiesCustomer(response, currentMessage);
		else
			setMessagePropertiesSupport(response, currentMessage);
		
		messages.add(currentMessage);
		currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true, true);
		return channel.basicGet(queueName, true);
		
	}
	private void setMessagePropertiesCustomer(GetResponse response, MessageEntity currentMessage) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = new Gson();
		MessageWrapperDTO mesaj = gson.fromJson(new String(response.getBody()), MessageWrapperDTO.class);
		currentMessage.getSender().setUsername(mesaj.getSenderName());// trb pus user mai apoi

		try {

			currentMessage.getMasina().setVin(mesaj.getIdCar());
			currentMessage.setDescriere(mesaj.getDescriere());
			currentMessage.setConversatie(mesaj.getConversatie());
			Date sentDate = format.parse(String.valueOf(response.getProps().getHeaders().get("sentDate")));
			Timestamp sentDateTimestamp = new Timestamp(sentDate.getTime());
			
			currentMessage.setSentDate(sentDateTimestamp);

			Date date = format.parse(format.format(new Date()));
			Timestamp x = new Timestamp(date.getTime());
			currentMessage.setReceivedDate(x);

			List<OfferEntity> oferte = new ArrayList<>();
			for (int i = 0; i < mesaj.getListaOferte().size(); i++) {
				OfferEntity ent = OfferDtoMapper.fromDTO(mesaj.getListaOferte().get(i));
				ent.setMesaj(currentMessage);
				oferte.add(ent);
			}
			currentMessage.setOferte(oferte);
			if (!mesaj.getIdMesaj().isEmpty())
				currentMessage.setId(Integer.parseInt(mesaj.getIdMesaj()));
			else
				currentMessage.setId(-1);

		} catch (ParseException e) {
			System.out.println("Parse Exception");
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
			MessageEntity currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true,
					true);

			GetResponse response = channel.basicGet(queueName, true);

			while (response != null) {
				setMessagePropertiesSupport(response, currentMessage);
				messages.add(currentMessage);
				currentMessage = new MessageEntity(new CarEntity(), new User(), receiver, null, "", true, true);
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
		currentMessage.getSender().setUsername(mesaj.getSenderName());// trb pus user mai apoi

		try {

			CarEntity car = CarMapper.toEntity(mesaj.getCar());
			car.setClient(currentMessage.getSender());

			currentMessage.setMasina(car);
			currentMessage.setConversatie(mesaj.getConversatie());
			currentMessage.setDescriere(mesaj.getDescriere());
			
			Date sentDate = format.parse(String.valueOf(response.getProps().getHeaders().get("sentDate")));
			Timestamp sentDateTimestamp = new Timestamp(sentDate.getTime());
			
			currentMessage.setSentDate(sentDateTimestamp);

			Date date = format.parse(format.format(new Date()));
			Timestamp x = new Timestamp(date.getTime());
			currentMessage.setReceivedDate(x);
			
			if (!mesaj.getIdMesaj().equals(""))
				currentMessage.setId(Integer.parseInt(mesaj.getIdMesaj()));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

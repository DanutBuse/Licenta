package com.rab.producer.ProducerRabbit.ServiceTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.rab.licenta.AutoClinique.constants.WebConstants;
import com.rab.licenta.AutoClinique.dto.CarDTO;
import com.rab.licenta.AutoClinique.dto.MessageWrapperDTO;
import com.rab.licenta.AutoClinique.dto.OfferDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.service.ConsumerService;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={ConsumerService.class})
public class ConsumerServiceTest {
	
	@Mock
	User user;
	
	@Mock
	Channel channel;
	
	@Mock
	GetResponse response;
	
	@Mock
	CarEntity masina;
	
	@Mock
	MessageEntity message;
	
	@Mock
	BasicProperties properties;
	
	@Mock
	List<MessageEntity> list;
	
	@InjectMocks
	public ConsumerService consumerService;
	
	
	@Before
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testIsQueueEmpty() {
		assertTrue(consumerService.isQueueEmpty("a"));
	}
	
	
	@Test
	public void fillListItemTest() throws IOException {
		Gson gson = new Gson();
		MessageWrapperDTO obj = new MessageWrapperDTO(Arrays.asList(new OfferDTO("a","b",3), new OfferDTO("a","b",3)), 
												"2", new CarDTO("a","a","a",3), "asd", "asd", "asd", "asd");
		Map<String, Object> map = new HashMap<>();
		map.put("sentDate", "2019-07-01 21:45:20.0");
	      
		when(user.getTipUtilizator()).thenReturn(WebConstants.CUSTOMER);
		when(response.getBody()).thenReturn(gson.toJson(obj).getBytes());
		when(message.getSender()).thenReturn(user);
		
		when(message.getMasina()).thenReturn(masina);
		when(response.getProps()).thenReturn(properties);
		when(properties.getHeaders()).thenReturn(map);
		when(list.add(message)).thenReturn(true);
		when(channel.basicGet("queueName", true)).thenReturn(response);
		
		assertNotNull(consumerService.fillListItem(list, user, response, message, channel, "queueName"));
	}
	
	@Test
	public void fillListItemIdMesajEmptyTest() throws IOException {
		Gson gson = new Gson();
		MessageWrapperDTO obj = new MessageWrapperDTO(Arrays.asList(new OfferDTO("a","b",3), new OfferDTO("a","b",3)), 
												"", new CarDTO("a","a","a",3), "asd", "asd", "asd", "asd");
		Map<String, Object> map = new HashMap<>();
		map.put("sentDate", "2019-07-01 21:45:20.0");
	      
		when(user.getTipUtilizator()).thenReturn(WebConstants.CUSTOMER);
		when(response.getBody()).thenReturn(gson.toJson(obj).getBytes());
		when(message.getSender()).thenReturn(user);
		
		when(message.getMasina()).thenReturn(masina);
		when(response.getProps()).thenReturn(properties);
		when(properties.getHeaders()).thenReturn(map);
		when(list.add(message)).thenReturn(true);
		when(channel.basicGet("queueName", true)).thenReturn(response);
		
		assertNotNull(consumerService.fillListItem(list, user, response, message, channel, "queueName"));
	}
	
	@Test
	public void fillListItemIdMesajSuportTest() throws IOException {
		Gson gson = new Gson();
		MessageWrapperDTO obj = new MessageWrapperDTO(Arrays.asList(new OfferDTO("a","b",3), new OfferDTO("a","b",3)), 
												"2", new CarDTO("a","a","a",3), "asd", "asd", "asd", "asd");
		Map<String, Object> map = new HashMap<>();
		map.put("sentDate", "2019-07-01 21:45:20.0");
	      
		when(user.getTipUtilizator()).thenReturn("Support");
		when(response.getBody()).thenReturn(gson.toJson(obj).getBytes());
		when(message.getSender()).thenReturn(user);
		
		when(response.getProps()).thenReturn(properties);
		when(properties.getHeaders()).thenReturn(map);
		when(list.add(message)).thenReturn(true);
		when(channel.basicGet("queueName", true)).thenReturn(response);
		
		assertNotNull(consumerService.fillListItem(list, user, response, message, channel, "queueName"));
	}
	
	@Test
	public void fillListItemIdMesajSuportEmptyTest() throws IOException {
		Gson gson = new Gson();
		MessageWrapperDTO obj = new MessageWrapperDTO(Arrays.asList(new OfferDTO("a","b",3), new OfferDTO("a","b",3)), 
												"", new CarDTO("a","a","a",3), "asd", "asd", "asd", "asd");
		Map<String, Object> map = new HashMap<>();
		map.put("sentDate", "2019-07-01 21:45:20.0");
	      
		when(user.getTipUtilizator()).thenReturn("Support");
		when(response.getBody()).thenReturn(gson.toJson(obj).getBytes());
		when(message.getSender()).thenReturn(user);
		
		when(response.getProps()).thenReturn(properties);
		when(properties.getHeaders()).thenReturn(map);
		when(list.add(message)).thenReturn(true);
		when(channel.basicGet("queueName", true)).thenReturn(response);
		
		assertNotNull(consumerService.fillListItem(list, user, response, message, channel, "queueName"));
	}

}

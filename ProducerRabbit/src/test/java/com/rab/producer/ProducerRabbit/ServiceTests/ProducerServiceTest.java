package com.rab.producer.ProducerRabbit.ServiceTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.boot.test.context.SpringBootTest;

import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.service.ProducerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={ProducerService.class})
public class ProducerServiceTest {

	@Mock
	public AmqpTemplate amqpTemplate;
	
	@InjectMocks
	public ProducerService producerService;
	
	@Before
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void produceMsgTest() {
		producerService.produceMsg("msg", "EXCHANGE_NAME", "ROUTING_KEY", "sender");
		verify(amqpTemplate, times(1)).send(any(String.class), any(String.class), any(Message.class));
	}

	@Test
	public void sendMessageInfoTest() {
		producerService.sendMessageInfo(new CarEntity("m", "t", new User(), "v", 2, null), "", "", "", "", "", "");
		verify(amqpTemplate, times(1)).send(any(String.class), any(String.class), any(Message.class));
	}
	
	@Test
	public void sendMessageInfoSecondTest() {
		producerService.sendMessageInfo("a", "a", "a", "4", "a", "a", "a","a");
		verify(amqpTemplate, times(1)).send(any(String.class), any(String.class), any(Message.class));
	}
	
	@Test
	public void sendMessageSupportInfoTest() {
		producerService.sendMessageSupportInfo("a", "a", "a", "4", "a", "a", "a","s",null,"");
		verify(amqpTemplate, times(1)).send(any(String.class), any(String.class), any(Message.class));
	}
	
	@Test
	public void sendMessageFromSupportTest() {
		producerService.sendMessageFromSupport(null, "a", "a", "4", "a", "a", "a","s");
		verify(amqpTemplate, times(1)).send(any(String.class), any(String.class), any(Message.class));
	}
	
}

package com.rab.producer.ProducerRabbit.ControllerTests;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.rab.licenta.AutoClinique.config.EmailCfg;
import com.rab.licenta.AutoClinique.constants.CookieConstants;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.ExchangeEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.QueueEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.service.ConsumerService;
import com.rab.licenta.AutoClinique.service.CookieService;
import com.rab.licenta.AutoClinique.service.DbService;
import com.rab.licenta.AutoClinique.service.ProducerService;
import com.rab.licenta.AutoClinique.web.WebController;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={WebController.class})
public class WebControllerTests {

	@Mock
	User user;
	
	@Mock
	QueueEntity queue;
	
	@Mock
	ExchangeEntity exchange;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	MessageEntity message;

	@Mock
	CarEntity car;
	
	@Mock
	DbService dbService;
	
	@Mock
	CookieService cookieService;
	
	@Mock
	ConsumerService consumerService;
	
	@Mock
	ProducerService producerService;
	
	@Mock
	EmailCfg emailCfg;
	
	@InjectMocks
	WebController webController;

	
	@Before
	public void setup() {
		
		when(dbService.getUserByName("a")).thenReturn(user);
		when(user.getUsername()).thenReturn("a");
		when(user.getQueue()).thenReturn(queue);
		when(user.getExchange()).thenReturn(exchange);
		when(queue.getQueueName()).thenReturn("a");
		when(exchange.getExchangeName()).thenReturn("a");
		when(exchange.getRoutingKey()).thenReturn("a");
	}
	
	@Test
	public void testInit() {
		assertNotNull(webController.init());
	}
	
	@Test
	public void testLogin() {
		assertNotNull(webController.loginView());
	}
	
	@Test
	public void testRegisterView() {
		assertNotNull(webController.registerView());
	}

	@Test
	public void testRegisterUserPost() {
		when(dbService.check("a")).thenReturn(true);
		assertNotNull(webController.registerUserPost("a", "a"));
	}
	
	@Test
	public void testRegisterUserPost2() {
		when(dbService.check("a")).thenReturn(false);
		assertNotNull(webController.registerUserPost("a", "a"));
	}
	
	@Test
	public void testAddUserPost() {
		when(dbService.check("a","a")).thenReturn(true);
		when(user.getTipUtilizator()).thenReturn("support");
		doNothing().when(consumerService).declareQueue("a", "a", "a");
		assertNotNull(webController.addUserPost("a", "a", response));
	}
	
	@Test
	public void testAddUserPos2t() {
		when(dbService.check("a","a")).thenReturn(false);
		assertNotNull(webController.addUserPost("a", "a", response));
	}
	
	@Test
	public void logoutTest() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		doNothing().when(user).setAvailable(false);
		doNothing().when(dbService).insertUser(user);
		doNothing().when(cookieService).setCookie(Mockito.any(), Mockito.any(), Mockito.any());
		assertNotNull(webController.logoutRequest(response, request));
	}
	
	@Test
	public void aboutUsTest() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		assertNotNull(webController.aboutUs(request));
	}
	
	@Test
	public void aboutUsTest2() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		assertNotNull(webController.aboutUs(request));
	}
	
	@Test
	public void produceInfoMenuTest() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("customer");
		assertNotNull(webController.produceInfoMenu(request));
	}
	
	@Test
	public void produceInfoMenu2Test() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("asd");
		assertNotNull(webController.produceInfoMenu(request));
	}
	
	@Test
	public void produceInfoMenu3Test() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		assertNotNull(webController.produceInfoMenu(request));
	}
	
	@Test
	public void sendMsgCustomerTest() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(dbService.getSuportsQueues()).thenReturn(null);
		when(producerService.getLowestNumberMessagesQueue(null)).thenReturn("asd");
		when(dbService.getUserByQueueName("asd")).thenReturn(user);
		doNothing().when(consumerService).declareQueue("a", "a", "a");
		doNothing().when(producerService).sendMessageInfo("m", "m", "asd", "2001", "asd", "a", "a", "a");
		assertNotNull(webController.sendMsgCustomer("m", "m", "asd", "2001", "asd", request));
	}
	
	@Test
	public void sendMsgCustome2rTest() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("asd");
		assertNotNull(webController.sendMsgCustomer("m", "m", "asd", "2001", "asd", request));
	}
	
	@Test
	public void sendMsgSupport() {
		Map<String, String> map = new HashMap<>();
		map.put("numepiesa0", "a");
		map.put("pret0", "12");
		map.put("producator0", "a");
		map.put("idMesaj", "0");
		map.put("descriere", "a");
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(dbService.getMessageById(0)).thenReturn(message);
		when(message.getMasina()).thenReturn(car);
		when(car.getVin()).thenReturn("a");
		when(message.getSender()).thenReturn(user);
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(message.getDescriere()).thenReturn("asd");
		assertNotNull(webController.sendMsgSupport(map, request));
	}
	
	@Test
	public void sendMsgSupport2() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("asd");
		assertNotNull(webController.sendMsgSupport(null, request));
	}
	
	@Test
	public void redirectToGoodPageTest() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("support");
		assertNotNull(webController.redirectToGoodConsumePage(request));
	}
	
	@Test
	public void redirectToGoodPageTest2() {
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("supportasd");
		assertNotNull(webController.redirectToGoodConsumePage(request));
	}
	
	@Test
	public void consumeAllMsgsCustomer() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(consumerService.receivedMessages(user)).thenReturn(list);
		when(message.getMasina()).thenReturn(car);
		when(car.getVin()).thenReturn("a");
		when(dbService.getMasinaById("a")).thenReturn(car);
		when(message.getId()).thenReturn(-1);
		doNothing().when(message).setMasina(car);
		doNothing().when(dbService).addOferte(message);
		doNothing().when(message).setReceived(true);
		doNothing().when(message).setSent(false);
		doNothing().when(dbService).insertOrUpdateCars(list);
		doNothing().when(dbService).updateMessages(list);
		when(dbService.messagesByReceiver(user)).thenReturn(list);
		when(message.isReceived()).thenReturn(true);
		assertNotNull(webController.consumeAllMsgCustomer(request));
	}
	
	@Test
	public void consumeAllMsgsCustomer2() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		assertNotNull(webController.consumeAllMsgCustomer(request));
	}
	
	@Test
	public void consumeAllMsgsSupport() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(consumerService.receivedMessages(user)).thenReturn(list);
		doNothing().when(message).setReceived(true);
		doNothing().when(message).setSent(false);
		doNothing().when(dbService).insertOrUpdateCars(list);
		doNothing().when(dbService).updateMessages(list);
		when(dbService.messagesByReceiver(user)).thenReturn(list);
		when(message.isReceived()).thenReturn(true);
		assertNotNull(webController.consumeAllMsgSupport(request));
	}
	
	@Test
	public void consumeAllMsgsSupport2() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		assertNotNull(webController.consumeAllMsgSupport(request));
	}
	
	@Test
	public void sentMsgsTest() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		assertNotNull(webController.sentMsgs(request));
	}
	
	@Test
	public void sentMsgsTest2() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		assertNotNull(webController.sentMsgs(request));
	}
	
	@Test
	public void supportReplyTest() {
		Map<String, String> map = new HashMap<>();
		map.put("numepiesa0", "a");
		map.put("pret0", "12");
		map.put("producator0", "a");
		map.put("mesajID", "0");
		map.put("descriere", "a");
		map.put("descriereAditionala", "a");
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(dbService.getMessageById(0)).thenReturn(message);
		when(message.getReceiver()).thenReturn(user);
		when(message.getSender()).thenReturn(user);
		when(user.getUsername()).thenReturn("asd");
		doNothing().when(message).setSender(user);
		doNothing().when(message).setReceiver(user);
		doNothing().when(dbService).insertMessage(message);
		when(message.getMasina()).thenReturn(car);
		when(car.getVin()).thenReturn("a");
		when(message.getConversatie()).thenReturn("asd");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(message.getDescriere()).thenReturn("asd");
		doNothing().when(consumerService).declareQueue("a", "a", "a");
		
		assertNotNull(webController.supportReply(request, map));
	}
	
	@Test
	public void getCustomerMessages() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(consumerService.receivedMessages(user)).thenReturn(list);
		doNothing().when(message).setReceived(true);
		doNothing().when(message).setSent(false);
		doNothing().when(dbService).insertOrUpdateCars(list);
		doNothing().when(dbService).updateMessages(list);
		when(dbService.messagesByReceiver(user)).thenReturn(list);
		when(message.isReceived()).thenReturn(true);
		when(message.getSender()).thenReturn(user);
		when(user.getUsername()).thenReturn("asd");
		assertNotNull(webController.getCustomerMessages(request, "asd"));
	}
	
	@Test
	public void getCustomerMessages2() {
		List<MessageEntity> list = new ArrayList<>();
		list.add(message);
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		assertNotNull(webController.getCustomerMessages(request, "asd"));
	}
	
	@Test
	public void getBroadCastViewTest() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("support");
		assertNotNull(webController.broadcastMessages(request));
	}
	
	@Test
	public void getBroadCastViewTest2() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		assertNotNull(webController.broadcastMessages(request));
	}
	
	@Test
	public void getBroadCastViewTest3() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("0");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		assertNotNull(webController.broadcastMessages(request));
	}
	
	@Test
	public void getBroadCastViewTest4() {
		when(cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY)).thenReturn("1");
		when(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).thenReturn("asd");
		when(dbService.getUserByName("asd")).thenReturn(user);
		when(user.getTipUtilizator()).thenReturn("suppoert");
		assertNotNull(webController.broadcastMessages(request));
	}
}

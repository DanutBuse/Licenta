package com.rab.producer.ProducerRabbit.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.constants.CookieConstants;
import com.rab.producer.ProducerRabbit.dto.OfertaDTO;
import com.rab.producer.ProducerRabbit.entity.MessageEntity;
import com.rab.producer.ProducerRabbit.entity.OfertaEntity;
import com.rab.producer.ProducerRabbit.entity.User;
import com.rab.producer.ProducerRabbit.service.ConsumerService;
import com.rab.producer.ProducerRabbit.service.CookieService;
import com.rab.producer.ProducerRabbit.service.DbService;
import com.rab.producer.ProducerRabbit.service.ProducerService;

@Controller
public class WebController {
	
	@Autowired
	ProducerService ProducerService;
	
	@Autowired
	ConsumerService ConsumerService;
	
	@Autowired
	DbService dbService;
	
	@Autowired
	CookieService cookieService;
	
	
	@RequestMapping(value = "/")
	public ModelAndView init() {
		return new ModelAndView("registerUser");
	}
	
	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public ModelAndView registerView() {
		return new ModelAndView("registerUser");
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ModelAndView registerUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									@RequestParam("typeName") String type)	{
		
		if(!dbService.check(userName)) {
			dbService.insertUser(userName,pass,type,null,null);
			
			User registeredUser = dbService.getUserByName(userName);
			
			dbService.setUserQueueExchange(registeredUser);
			
		}
		else
			return new ModelAndView("registerUser");
		
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	public ModelAndView loginView() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ModelAndView addUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									HttpServletResponse response)	{
		
		ModelAndView mv = new ModelAndView("produceInfo");
		
		User user = dbService.getUserByName(userName);
		
		if(dbService.check(user,pass)) {
			
			if(user.getTipUtilizator().equals("support")) {
				user.setAvailable(true);
				dbService.insertUser(user);
			}
			
			ConsumerService.declareQueue(user.getQueue().getQueueName(),
					 user.getExchange().getExchangeName(),
					 user.getExchange().getRoutingKey());
			
			cookieService.setCookie(response, CookieConstants.LOGGED_IN_KEY, CookieConstants.LOGGED_IN_VALUE_GOOD);
			cookieService.setCookie(response, CookieConstants.USERNAME_KEY, userName);
			
			if(user.getTipUtilizator().equals("support")) {
				user.setAvailable(true);
				dbService.insertUser(user);
				return new ModelAndView("supportCreateMessage");
			}
		}
		else
		{
			cookieService.setCookie(response, CookieConstants.LOGGED_IN_KEY, CookieConstants.LOGGED_IN_VALUE_BAD);
			return new ModelAndView("login");
		}
		
		return mv;
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logoutRequest(HttpServletResponse response,
									  HttpServletRequest request){
		ModelAndView mv = new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		//List<MessageEntity> messages = ConsumerService.receivedMessages(receiver);
		
		receiver.setAvailable(false);
		//dbService.insertMessages(messages);
		dbService.insertUser(receiver);
		
		//ConsumerService.deleteQueue(receiver.getQueue().getQueueName()); nu merge
		
		cookieService.setCookie(response, CookieConstants.LOGGED_IN_KEY, CookieConstants.LOGGED_IN_VALUE_BAD);
		cookieService.setCookie(response, CookieConstants.USERNAME_KEY, "");
		
		return mv;
	}
	
	@RequestMapping(value = "/menu",method = RequestMethod.GET)
	public ModelAndView produceInfoMenu(HttpServletRequest request) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		String username = cookieService.getCookieValue(request, CookieConstants.USERNAME_KEY);
		String tip = dbService.getUserByName(username).getTipUtilizator();
		
		if(loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			if(tip.equals("customer"))
				return new ModelAndView("produceInfo");
			else
				return new ModelAndView("supportCreateMessage");
		else
			return new ModelAndView("login");
	
	}
	

	@RequestMapping(value = "/sendMessageCustomer", method = RequestMethod.POST)
	public ModelAndView sendMsgCustomer(@RequestParam("marca") String marca,
								@RequestParam("tip") String tip,
								@RequestParam("vin") String vin,
								@RequestParam("an") String an,
								@RequestParam("descriere") String descriere,
								HttpServletRequest request){
	
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);

		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User sender = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<String> queues = dbService.getSuportsQueues();
		
		String lowestMessagesQueue = ProducerService.getLowestNumberMessagesQueue(queues);
		
		User receiver = dbService.getUserByQueueName(lowestMessagesQueue);
		
		ConsumerService.declareQueue(receiver.getQueue().getQueueName(), 
									 sender.getExchange().getExchangeName(),
									 receiver.getExchange().getRoutingKey());
		
		ProducerService.sendMessageInfo(marca, tip, vin, an, "", descriere,
										 sender.getExchange().getExchangeName(),
								   		 receiver.getExchange().getRoutingKey(),
								   		 sender.getUsername());
		
		ModelAndView mv = new ModelAndView("sentMessages");
	//	mv.addObject("data", "true");
		
		return mv;
	}
	
	@RequestMapping(value = "/sendMessageSupport", method = RequestMethod.POST)
	public ModelAndView sendMsgSupport(@RequestParam Map<String, String> parameters,
								HttpServletRequest request){
	
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);

		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		String idMesaj = parameters.get("idMesaj");
		String idMasina = dbService.getMessageById(Integer.parseInt(idMesaj)).getMasina().getId().toString();
		
		User customer = dbService.getMessageById(Integer.parseInt(idMesaj)).getSender();
		User sender = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		String descriereSuplimentara = parameters.get("descriere");
		
		ArrayList<OfertaDTO> listaOferte = new ArrayList<OfertaDTO>();
		for(int i = 1; i <= (parameters.keySet().size()-2)/3; i++) {
			
			OfertaDTO oferta = new OfertaDTO();
			
			oferta.setNumePiesa(parameters.get("numepiesa" + i));
			oferta.setPret(Integer.parseInt(parameters.get("pret" + i)));
			oferta.setProducator(parameters.get("producator" + i));
			
			listaOferte.add(oferta);
			
		}
		
		String descriereVeche =  dbService.getMessageById(Integer.parseInt(idMesaj)).getDescriere();
		
		ProducerService.sendMessageFromSupport(listaOferte, idMesaj, descriereSuplimentara,
											   sender.getExchange().getExchangeName(),
											   customer.getExchange().getRoutingKey(), sender.getUsername(), descriereVeche,
											   idMasina);
		
		ModelAndView mv = new ModelAndView("sentMessages");
		
		return mv;
	}
	
//	@RequestMapping(value = "/consumeAll" , method = RequestMethod.GET)
//	public ModelAndView consumeAllMsg(HttpServletRequest request) {
//		
//		ModelAndView mv = new ModelAndView("consumedAll");
//		
//		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
//		
//		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
//			return new ModelAndView("login");
//		
//		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
//		
//		List<MessageEntity> messages = ConsumerService.receivedMessages(receiver);
//		
//		dbService.insertCars(messages);
//		dbService.insertMessages(messages);
//		
//		messages.stream().forEach( m -> dbService.addOferte(m));
//		
//		mv.addObject("messages", dbService.messagesByReceiver(receiver));
//		
//		return mv;
//		
//	}
	@RequestMapping(value = "/consumeAll", method = RequestMethod.GET)
	public String redirectToGoodConsumePage(HttpServletRequest request) {
		
		String tip = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY)).getTipUtilizator();
		
		if(tip.equals("support")) {
			return "redirect:consumeAllSupport";
		}else
			return "redirect:consumeAllCustomer";
		
	}
	
	@RequestMapping(value = "/consumeAllCustomer" , method = RequestMethod.GET)
	public ModelAndView consumeAllMsg(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("consumedAll");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<MessageEntity> messages = ConsumerService.receivedMessagesCustomer(receiver);
		
		messages.stream().forEach( m -> m.setMasina(dbService.getMasinaById(m.getMasina().getId())));
		
		dbService.insertCars(messages);
		dbService.insertMessagesCustomer(messages);
		
		messages.stream().forEach( m -> dbService.addOferte(m));
		
		mv.addObject("messages", dbService.messagesByReceiver(receiver));
		
		return mv;
		
	}
	
	@RequestMapping(value = "/consumeAllSupport" , method = RequestMethod.GET)
	public ModelAndView consumeAllMsgSupport(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("consumedAll");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<MessageEntity> messages = ConsumerService.receivedMessagesSupport(receiver);
		
		dbService.insertCars(messages);
		dbService.insertMessagesCustomer(messages);
		
		mv.addObject("messages", dbService.messagesByReceiver(receiver));
		
		return mv;
		
	}
	
	@RequestMapping(value = "/sentMessages" , method = RequestMethod.GET)
	public ModelAndView sentMsgs(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("sentMessages");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User loggedUser = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		mv.addObject("messages",dbService.sentMessagesBy(loggedUser));
		
		return mv;
		
	}
	
}


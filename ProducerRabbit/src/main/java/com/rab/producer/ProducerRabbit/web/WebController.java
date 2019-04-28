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

import com.rab.producer.ProducerRabbit.OfertaDtoMapper;
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
	public String sendMsgCustomer(@RequestParam("marca") String marca,
								@RequestParam("tip") String tip,
								@RequestParam("vin") String vin,
								@RequestParam("an") String an,
								@RequestParam("descriere") String descriere,
								HttpServletRequest request){
	
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);

		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
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
		
		return "redirect:sentMessages";
	}
	
	@RequestMapping(value = "/sendMessageSupport", method = RequestMethod.POST)
	public String sendMsgSupport(@RequestParam Map<String, String> parameters,
								HttpServletRequest request){
	
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);

		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
		String idMesaj = parameters.get("idMesaj");
		String idMasina = dbService.getMessageById(Integer.parseInt(idMesaj)).getMasina().getVin();
		
		User customer = dbService.getMessageById(Integer.parseInt(idMesaj)).getSender();
		User sender = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		String descriereSuplimentara = parameters.get("descriere");
		
		long numarPiese =parameters.keySet().stream()
											.filter( o -> o.contains("numepiesa"))
											.count();
		
		ArrayList<OfertaDTO> listaOferte = new ArrayList<OfertaDTO>();
		for(int i = 0; i < numarPiese; i++) {
			
			OfertaDTO oferta = new OfertaDTO();
			
			oferta.setNumePiesa(parameters.get("numepiesa" + (i+1)));
			oferta.setPret(Integer.parseInt(parameters.get("pret" + (i+1))));
			oferta.setProducator(parameters.get("producator" + (i+1)));
			
			listaOferte.add(oferta);
			
		}
		
		String descriereVeche =  dbService.getMessageById(Integer.parseInt(idMesaj)).getDescriere();
		
		ProducerService.sendMessageFromSupport(listaOferte, idMesaj, descriereSuplimentara,
											   sender.getExchange().getExchangeName(),
											   customer.getExchange().getRoutingKey(), sender.getUsername(), descriereVeche,
											   idMasina);
		
		return "redirect:sentMessages";
	}
	
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
		
		ModelAndView mv = new ModelAndView("consumedAllCustomer");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<MessageEntity> messages = ConsumerService.receivedMessagesCustomer(receiver);
		
		messages.stream().forEach( m -> m.setMasina(dbService.getMasinaById(m.getMasina().getVin())));
		messages.stream().forEach( m -> dbService.getMessageById(m.getId()).getOferte().stream().forEach( o -> m.getOferte().add(o)));
		
		dbService.insertCars(messages);
		dbService.insertMessagesCustomer(messages);
		
		messages.stream().forEach( m -> { dbService.addOferte(m);
										  m.setReceived(true);
										  m.setSent(false);});
		
		mv.addObject("messages", dbService.messagesByReceiver(receiver).stream().filter( m -> m.isReceived()).toArray());
		
		return mv;
		
	}
	
	@RequestMapping(value = "/consumeAllSupport" , method = RequestMethod.GET)
	public ModelAndView consumeAllMsgSupport(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("consumedAllSupport");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<MessageEntity> messages = ConsumerService.receivedMessagesSupport(receiver);
		
		messages.stream().forEach( m -> {m.setReceived(true);
										 m.setSent(false);});
		dbService.insertCars(messages);
		dbService.insertMessagesCustomer(messages);
		
		mv.addObject("messages", dbService.messagesByReceiver(receiver).stream().filter( m -> m.isReceived()).toArray());
		
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
	
	@RequestMapping(value = "/sendReplyFromSupport" , method = RequestMethod.POST)
	public String supportReply(HttpServletRequest request, @RequestParam Map<String, String> parameters) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
		String descriereAditionala = parameters.get("descriereAditionala");
		String mesajId = parameters.get("mesajID");
		
		MessageEntity mes = dbService.getMessageById(Integer.parseInt(mesajId));
		mes.setSent(true);
		mes.setReceived(false);
		dbService.insertMessage(mes);
		
		long numarPiese =parameters.keySet().stream()
						   					.filter( o -> o.contains("numepiesa"))
						   					.count();
		
		ArrayList<OfertaDTO> listaOferteSuplimentare = new ArrayList<OfertaDTO>();
		
		if(!(parameters.get("numepiesa1").equals("") || parameters.get("producator1").equals("") || parameters.get("pret1").equals(""))) {
			for(int i = 0; i < numarPiese; i++) {
				
				OfertaDTO oferta = new OfertaDTO();
				
				oferta.setNumePiesa(parameters.get("numepiesa" + (i+1)));
				oferta.setPret(Integer.parseInt(parameters.get("pret" + (i+1))));
				oferta.setProducator(parameters.get("producator" + (i+1)));
				
				listaOferteSuplimentare.add(oferta);
				
			}
		}	
		
		User support = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		String idMasina = dbService.getMessageById(Integer.parseInt(mesajId)).getMasina().getVin();
		User customer = dbService.getMessageById(Integer.parseInt(mesajId)).getSender();
		String descriereVeche = dbService.getMessageById(Integer.parseInt(mesajId)).getDescriere();
		
		
		
		ProducerService.sendMessageFromSupport(listaOferteSuplimentare, mesajId, descriereAditionala,
				   support.getExchange().getExchangeName(),
				   customer.getExchange().getRoutingKey(), support.getUsername(), descriereVeche,
				   idMasina);
		
		return "redirect:sentMessages";
		
	}
	
	@RequestMapping(value = "/sendReplyFromCustomer" , method = RequestMethod.POST)
	public String customerReply(HttpServletRequest request, @RequestParam Map<String, String> parameters) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
		String descriereAditionala = parameters.get("descriereAditionala");
		String mesajId = parameters.get("mesajID");
		
		MessageEntity mes = dbService.getMessageById(Integer.parseInt(mesajId));
		mes.setSent(true);
		mes.setReceived(false);
		dbService.insertMessage(mes);
		
		User customer = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		User support = dbService.getMessageById(Integer.parseInt(mesajId)).getSender();
		String descriereVeche = dbService.getMessageById(Integer.parseInt(mesajId)).getDescriere();
		
		ConsumerService.declareQueue(support.getQueue().getQueueName(), 
				customer.getExchange().getExchangeName(),
				 support.getExchange().getRoutingKey());

		ProducerService.sendMessageInfo(dbService.getMessageById(Integer.parseInt(mesajId)).getMasina(),
							 customer.getExchange().getExchangeName(),
							 support.getExchange().getRoutingKey(),
							 customer.getUsername(), descriereVeche+descriereAditionala, mesajId);
		
		return "redirect:sentMessages";
		
	}
	
}


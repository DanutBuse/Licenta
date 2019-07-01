package com.rab.licenta.AutoClinique.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.licenta.AutoClinique.constants.CookieConstants;
import com.rab.licenta.AutoClinique.dto.OfferDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.service.ConsumerService;
import com.rab.licenta.AutoClinique.service.CookieService;
import com.rab.licenta.AutoClinique.service.DbService;
import com.rab.licenta.AutoClinique.service.ProducerService;
import com.rab.licenta.AutoClinique.utils.DateParsers;

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
		
		if(dbService.check(userName,pass)) {	
			
			User user = dbService.getUserByName(userName);
			cookieService.setCookie(response, CookieConstants.LOGGED_IN_KEY, CookieConstants.LOGGED_IN_VALUE_GOOD);
			cookieService.setCookie(response, CookieConstants.USERNAME_KEY, userName);
			
			
			dbService.setUserQueueExchange(user);
			
			ConsumerService.declareQueue(user.getQueue().getQueueName(),
					user.getExchange().getExchangeName(),
					user.getExchange().getRoutingKey());
			
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
		
		List<String> queues = dbService.getSuportsQueues();// verificare existenta coada
		String lowestMessagesQueue = ProducerService.getLowestNumberMessagesQueue(queues);
		
		User receiver = dbService.getUserByQueueName(lowestMessagesQueue);
		
		ConsumerService.declareQueue(receiver.getQueue().getQueueName(), 
									 sender.getExchange().getExchangeName(),
									 receiver.getExchange().getRoutingKey());
		
		ProducerService.sendMessageInfo(marca, tip, vin, an, descriere,
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
		
		long numarPiese = parameters.keySet().stream()
											.filter( o -> o.contains("numepiesa"))
											.count();
		
		ArrayList<OfferDTO> listaOferte = new ArrayList<OfferDTO>();
		for(int i = 0; i < numarPiese; i++) {
			
			OfferDTO oferta = new OfferDTO();
			if(parameters.get("numepiesa" + i).isEmpty() || parameters.get("pret" + i).isEmpty()
					|| parameters.get("producator" + i).isEmpty()) {
				
				continue;
				
			}else {
				oferta.setNumePiesa(parameters.get("numepiesa" + i));
				oferta.setPret(Integer.parseInt(parameters.get("pret" + i)));
				oferta.setProducator(parameters.get("producator" + i));
				listaOferte.add(oferta);
			}
			
			
		}
		
		String descriereVeche =  dbService.getMessageById(Integer.parseInt(idMesaj)).getDescriere();
		
		ConsumerService.declareQueue(customer.getQueue().getQueueName(), 
									 sender.getExchange().getExchangeName(),
									 customer.getExchange().getRoutingKey());
		
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
	public ModelAndView consumeAllMsgCustomer(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("consumedAllCustomer");
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		User receiver = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		List<MessageEntity> messages = ConsumerService.receivedMessages(receiver);
		
		messages.stream().forEach( m -> {
									m.setMasina(dbService.getMasinaById(m.getMasina().getVin()));
									if(m.getId() != -1)
										dbService.getMessageById(m.getId()).getOferte().stream().forEach( o -> m.getOferte().add(o));
									dbService.addOferte(m);
									m.setReceived(true);
									m.setSent(false);
								  });
		
		dbService.insertOrUpdateCars(messages);
		dbService.updateMessages(messages);
		
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
		
		List<MessageEntity> messages = ConsumerService.receivedMessages(receiver);
		
		messages.stream().forEach( m -> {m.setReceived(true);
										 m.setSent(false);});
		
		dbService.insertOrUpdateCars(messages);
		dbService.updateMessages(messages);
		
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
		
		mv.addObject("messages",dbService.sentMessagesBy(loggedUser).stream().filter( m -> m.isReceived()).toArray());
		
		return mv;
		
	}
	
	@RequestMapping(value = "/sendReplyFromSupport" , method = RequestMethod.POST)
	public String supportReply(HttpServletRequest request, @RequestParam Map<String, String> parameters) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
		
		String mesajId = parameters.get("mesajID");
		
		MessageEntity mes = dbService.getMessageById(Integer.parseInt(mesajId));
		mes.setSent(true);
		mes.setReceived(false);
		
		User customer = mes.getSender();
		String raspuns = "Sent Date: "+DateParsers.formatDate(new Date()) 
									+"    From:"+customer.getUsername()
									+"    Message:"+parameters.get("descriereAditionala")+"\n";
		
		mes.setSender(mes.getReceiver());
		mes.setReceiver(customer);
		
		dbService.insertMessage(mes);
		
		long numarPiese = parameters.keySet().stream()
						   					 .filter( o -> o.contains("numepiesa"))
						   					 .count();
		
		ArrayList<OfferDTO> listaOferteSuplimentare = new ArrayList<OfferDTO>();
		
			for(int i = 0; i < numarPiese; i++) {
				if(parameters.get("numepiesa" + i).isEmpty() || parameters.get("pret" + i).isEmpty()
						|| parameters.get("producator" + i).isEmpty()) {
					
					continue;
				}else {
					OfferDTO oferta = new OfferDTO();
					
					oferta.setNumePiesa(parameters.get("numepiesa" + i));
					oferta.setPret(Integer.parseInt(parameters.get("pret" + i)));
					oferta.setProducator(parameters.get("producator" + i));
					listaOferteSuplimentare.add(oferta);
				}
			
			}
		
		
		User support = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		String idMasina = dbService.getMessageById(Integer.parseInt(mesajId)).getMasina().getVin();
		String descriere = dbService.getMessageById(Integer.parseInt(mesajId)).getDescriere();
		String conversatie = dbService.getMessageById(Integer.parseInt(mesajId)).getConversatie()+raspuns;
		
		ConsumerService.declareQueue(customer.getQueue().getQueueName(), 
				 support.getExchange().getExchangeName(),
				 customer.getExchange().getRoutingKey());
		
		ProducerService.sendMessageFromSupport(listaOferteSuplimentare, mesajId, conversatie,
				   support.getExchange().getExchangeName(),
				   customer.getExchange().getRoutingKey(), support.getUsername(), descriere,
				   idMasina);
		
		return "redirect:sentMessages";
		
	}
	
	@RequestMapping(value = "/sendReplyFromCustomer" , method = RequestMethod.POST)
	public String customerReply(HttpServletRequest request, @RequestParam Map<String, String> parameters) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return "redirect:menu";
		
		Map<String, String> pieseComandate = new HashMap<String, String>();
		
		parameters.keySet().stream()
						 .filter( o -> o.contains("comandat"))
						 .forEach(o -> pieseComandate.put(o, parameters.get(o)));
		
		String comanda = "";
		for(Entry ent : pieseComandate.entrySet()) {
			if(ent.getValue().equals("on")) {
				comanda = comanda + "\n\t\t\t**Ordered** Part Number:" + ent.getKey().toString().replace("comandat", "") + "\n";
			}
			else
				continue;
		}
		
		String mesajId = parameters.get("mesajID");
		
		MessageEntity mes = dbService.getMessageById(Integer.parseInt(mesajId));
		mes.setSent(true);
		mes.setReceived(false);
		
		User support = mes.getSender();
		String raspuns = "Sent Date: " + DateParsers.formatDate(new Date()) 
									+"    From:" + support.getUsername()
									+"    Message:" + comanda;
		
	    raspuns = raspuns + "Sent Date: " + DateParsers.formatDate(new Date()) 
						+"    From:" + support.getUsername()
						+"    Message:" + parameters.get("descriereAditionala") + "\n";
		
		mes.setSender(mes.getReceiver());
		mes.setReceiver(support);
		
		dbService.insertMessage(mes);
		
		User customer = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		String descriere = dbService.getMessageById(Integer.parseInt(mesajId)).getDescriere();
		String conversatie = dbService.getMessageById(Integer.parseInt(mesajId)).getConversatie()+raspuns;
		
		ConsumerService.declareQueue(support.getQueue().getQueueName(), 
									 customer.getExchange().getExchangeName(),
									 support.getExchange().getRoutingKey());

		ProducerService.sendMessageInfo(dbService.getMessageById(Integer.parseInt(mesajId)).getMasina(),
										 customer.getExchange().getExchangeName(),
										 support.getExchange().getRoutingKey(),
										 customer.getUsername(), descriere, mesajId, conversatie);
				
		return "redirect:sentMessages";
		
	}
	@RequestMapping(value = "/getCustomerMessages" , method = RequestMethod.GET)
	public ModelAndView getCustomerMessages(HttpServletRequest request, @RequestParam("customerNameInput") String customerName) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		User support = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));

		ModelAndView mv = new ModelAndView("supportCreateMessage");
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD))
			return new ModelAndView("login");
		
		List<MessageEntity> messages = ConsumerService.receivedMessages(support);
		
		messages.stream().forEach( m -> {m.setReceived(true);
										 m.setSent(false);});
		
		dbService.insertOrUpdateCars(messages);
		dbService.updateMessages(messages);
		
		return mv.addObject("messages", dbService.messagesByReceiver(support).stream()
																	  .filter( m -> m.isReceived() && m.getSender().getUsername().equals(customerName))
																	  .toArray());
	}
	
	@RequestMapping(value = "/broadcast" , method = RequestMethod.GET)
	public ModelAndView broadcastMessages(HttpServletRequest request) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		User support = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));

		ModelAndView mv = new ModelAndView("broadcastMessageView");
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD) || !support.getTipUtilizator().equals("support"))
			return new ModelAndView("login");
		
		return mv;
		
	}
	
	@RequestMapping(value = "/broadcastOffers", method = RequestMethod.POST)
	public String sendBrodcastOffers(@RequestParam Map<String, String> parameters,
								HttpServletRequest request){
	
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		User support = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
		
		if(!loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD) || !support.getTipUtilizator().equals("support"))
			return "redirect:menu";
				
		String descriere = "BROADCAST:" + parameters.get("descriere");
		String marca = parameters.get("marca");
		String tip = parameters.get("tip");
		String an = parameters.get("an");
		
		long numarPiese = parameters.keySet().stream()
											.filter( o -> o.contains("numepiesa"))
											.count();
		
		ArrayList<OfferDTO> listaOferte = new ArrayList<OfferDTO>();
		for(int i = 0; i < numarPiese; i++) {
			
			OfferDTO oferta = new OfferDTO();
			if(parameters.get("numepiesa" + i).isEmpty() || parameters.get("pret" + i).isEmpty()
					|| parameters.get("producator" + i).isEmpty()) {
				
				continue;
				
			}else {
				oferta.setNumePiesa(parameters.get("numepiesa" + i));
				oferta.setPret(Integer.parseInt(parameters.get("pret" + i)));
				oferta.setProducator(parameters.get("producator" + i));
				listaOferte.add(oferta);
			}
			
			
		}
		
		List<CarEntity> carList = dbService.getCarsByTypeYear(marca, tip, an);
		
		carList.stream().forEach(c -> {
			
			ConsumerService.declareQueue(c.getClient().getQueue().getQueueName(), 
										 support.getExchange().getExchangeName(),
										 c.getClient().getExchange().getRoutingKey());

			ProducerService.sendMessageSupportInfo(marca, tip, c.getVin(), an, descriere,
													 support.getExchange().getExchangeName(),
													 c.getClient().getExchange().getRoutingKey(),
											   		 support.getUsername(), listaOferte, "");
								});
		
		return "redirect:broadcast";
	}
}


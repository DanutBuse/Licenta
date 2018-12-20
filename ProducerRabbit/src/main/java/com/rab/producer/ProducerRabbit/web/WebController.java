package com.rab.producer.ProducerRabbit.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.entity.User;
import com.rab.producer.ProducerRabbit.service.ConsumerService;
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
	
	
	
	//se inreg
	@RequestMapping(value = "/")
	public ModelAndView init() {
		//return "redirect:add/user";
		return new ModelAndView("registerUser");
	}
	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public ModelAndView registerView() {
		return new ModelAndView("registerUser");
	}
	//baga in DB si trimite login.jsp
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ModelAndView registerUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									@RequestParam("tip") String type)	{
		
		ModelAndView mv = new ModelAndView("registerUser");
		
		dbService.insertUser(userName,pass,type,null,null);
		
		return mv;
	}
	
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	public ModelAndView loginView() {
		return new ModelAndView("login");
	}
	
	//se logheaza
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ModelAndView addUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									HttpServletResponse response)	{
		
		//view menu
		ModelAndView mv = new ModelAndView("produceInfo");
		
		User user = dbService.getUserByName(userName);
		if(dbService.check(user,pass)) {
			response.addCookie(new Cookie("loggedIn","1"));
			response.addCookie(new Cookie("UserName",userName));
		}
		else
		{
			response.addCookie(new Cookie("loggedIn","0"));
			return new ModelAndView("login");
		}
		
		dbService.setUserQueueExchange(user);
		
		return mv;
	}
	@RequestMapping(value = "/menu",method = RequestMethod.GET)
	public ModelAndView produceInfoMenu(HttpServletRequest request) {
		
		List<Cookie> cookies = Arrays.asList(request.getCookies());
		String loggedIn = cookies.stream()
								 .filter(x -> x.getName().equals("loggedIn") )
								 .map(x -> x.getValue())
								 .findFirst()
								 .get();
	
		if(loggedIn.equals("1"))
			return new ModelAndView("produceInfo");
		else
			return new ModelAndView("login");
	
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView sendMsg(@RequestParam("name") String data,
								@RequestParam("receiver") String receiverName,
								HttpServletRequest request){
	
		List<Cookie> cookies = Arrays.asList(request.getCookies());
		
		User loggedUser = dbService.getUserByName(cookies.stream()
														 .filter(x -> x.getName().equals("UserName") )
														 .map(x -> x.getValue())
														 .findFirst()
														 .get());
		
		User receiver = dbService.getUserByName(receiverName);
		
		ConsumerService.declareQueue(receiver.getQueue().getQueueName(), 
				  loggedUser.getExchange().getExchangeName(),
				  receiver.getExchange().getRoutingKey());
		
		ProducerService.produceMsg(data,loggedUser.getExchange().getExchangeName()
							,receiver.getExchange().getRoutingKey());
		
		ModelAndView mv = new ModelAndView("sent");
		mv.addObject("data", data);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/consume" , method = RequestMethod.POST)
	public ModelAndView consumeMsg(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("consumed");
		List<Cookie> cookies = Arrays.asList(request.getCookies());
		
		User loggedUser = dbService.getUserByName(cookies.stream()
														 .filter(x -> x.getName().equals("UserName") )
														 .map(x -> x.getValue())
														 .findFirst()
														 .get());
		
		mv.addObject("message", ConsumerService.recievedMessage(loggedUser.getQueue().getQueueName()));
		return mv;
		
	}
	
}


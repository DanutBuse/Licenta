package com.rab.producer.ProducerRabbit.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.consumer.Consumer;
import com.rab.producer.ProducerRabbit.entity.User;
import com.rab.producer.ProducerRabbit.producer.Producer;
import com.rab.producer.ProducerRabbit.service.DbService;



@Controller
public class WebController {
	
	@Autowired
	Producer producer;
	@Autowired
	Consumer consumer;
	@Autowired
	DbService dbService;
	
	
	
	//se inreg
	@RequestMapping(value = "/")
	public ModelAndView init() {
		//return "redirect:add/user";
		return new ModelAndView("registerUser");
	}
	//baga in DB si trimite login.jsp
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView registerUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									@RequestParam("tip") String type)	{
		
		ModelAndView mv = new ModelAndView("login");
		
		User user = new User(userName,pass,type,null,null);
		dbService.insertUser(user);
		
		return mv;
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
		
		consumer.declareQueue(receiver.getQueue().getQueueName(), 
				  loggedUser.getExchange().getExchangeName(),
				  loggedUser.getExchange().getRoutingKey());
		
		producer.produceMsg(data,loggedUser.getExchange().getExchangeName()
							,loggedUser.getExchange().getRoutingKey());
		
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
		
		mv.addObject("message", consumer.recievedMessage(loggedUser.getQueue().getQueueName()));
		return mv;
		
	}
	
}


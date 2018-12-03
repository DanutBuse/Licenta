package com.rab.producer.ProducerRabbit.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
	
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView sendMsg(@ModelAttribute("name") String name){
		producer.produceMsg(name,dbService.getUserById(1).getExchange().getExchangeName(),dbService.getUserById(1).getExchange().getRoutingKey());
		ModelAndView mv = new ModelAndView("sent");
		return mv;
	}
	
	@RequestMapping(value = "/")
	public ModelAndView init() {
		//return "redirect:add/user";
		return new ModelAndView("registerUser");
	}
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public ModelAndView addUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									HttpServletResponse response)	{
		
		//view menu
		ModelAndView mv = new ModelAndView("sent");
		
		User user = dbService.getUserByName(userName);
		if(dbService.check(user,pass)) {
			response.addCookie(new Cookie("loggedIn","1"));
		}
		else
			{
				response.addCookie(new Cookie("loggedIn","0"));
				return new ModelAndView("login");
			}
		dbService.setUserQueueExchange(user);
		
		consumer.declareQueue(user.getQueue().getQueueName(), 
							  user.getExchange().getExchangeName(),
							  user.getExchange().getRoutingKey());
		
		return mv;
	}
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView registerUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									@RequestParam("tip") String type)	{
		
		ModelAndView mv = new ModelAndView("login");
		
		User user = new User(userName,pass,type,null,null);
		dbService.insertUser(user);
		
		return mv;
	}
	@RequestMapping("/produce")
	public ModelAndView produce(){
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		return mv;
	}
	
	@RequestMapping(value = "/consume" , method = RequestMethod.POST)
	public ModelAndView consumeMsg() {
		ModelAndView mv = new ModelAndView("consumed");
		mv.addObject("message", consumer.recievedMessage());
		return mv;
		
	}
}


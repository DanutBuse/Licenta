package com.rab.producer.ProducerRabbit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.consumer.Consumer;
import com.rab.producer.ProducerRabbit.entity.ExchangeEntity;
import com.rab.producer.ProducerRabbit.entity.QueueEntity;
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
	public String init() {
		return "redirect:add/user";
	}
	@RequestMapping(value = "/add/user",method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView mv = new ModelAndView("addUser");
		return mv;
	}
	
	@RequestMapping(value = "/add/user",method = RequestMethod.POST)
	public ModelAndView addUserPost(@RequestParam("username") String userName,
									@RequestParam("pass") String pass,
									@RequestParam("tip") String type)	{
		
		ModelAndView mv = new ModelAndView("addUser");
		
		ExchangeEntity exchangeEntity = new ExchangeEntity("jsa.direct2","jsa.rountingkey");
		QueueEntity queueEntity = new QueueEntity("queue2");
		User user = new User(userName,pass,type,exchangeEntity,queueEntity);
		dbService.insertUser(user);
		
		consumer.declareQueue(user.getQueue().getQueueName(), 
							  user.getExchange().getExchangeName(),
							  user.getExchange().getRoutingKey());
		
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


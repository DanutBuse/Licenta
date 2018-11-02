package com.rab.producer.ProducerRabbit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.consumer.Consumer;
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
		producer.produceMsg(name,user.getExchange(),user.getRoutingKey());
		ModelAndView mv = new ModelAndView("sent");
		return mv;
	}
	
	@RequestMapping(value = "/")
	public String init() {
		return "redirect:produce";
	}
	@RequestMapping(value = "/add/user",method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView mv = new ModelAndView("addUser");
		return mv;
	}
	
	@RequestMapping(value = "/add/user",method = RequestMethod.POST)
	public ModelAndView addUserPost(@RequestParam("Username") String userName,
									@RequestParam("Password") String pass,
									@RequestParam("Type") String Type) {
		ModelAndView mv = new ModelAndView("addUser");
		
		return mv;
	}
	
	@RequestMapping("/produce")
	public ModelAndView produce(){
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		user.setQueue("queue2");
		user.setRoutingKey("jsa.rountingkey");
		user.setExchange("jsa.direct2");
		
		consumer.declareQueue(user.getQueue(), user.getExchange(),user.getRoutingKey());
		
		return mv;
	}
	
	@RequestMapping(value = "/consume" , method = RequestMethod.POST)
	public ModelAndView consumeMsg() {
		ModelAndView mv = new ModelAndView("consumed");
		mv.addObject("message", consumer.recievedMessage());
		return mv;
		
	}
}


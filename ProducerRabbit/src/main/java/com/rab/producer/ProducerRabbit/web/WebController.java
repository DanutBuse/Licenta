package com.rab.producer.ProducerRabbit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rab.producer.ProducerRabbit.consumer.Consumer;
import com.rab.producer.ProducerRabbit.entity.User;
import com.rab.producer.ProducerRabbit.producer.Producer;



@Controller
public class WebController {
	
	@Autowired
	Producer producer;
	@Autowired
	Consumer consumer;
	@Autowired
	User user;
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView sendMsg(@ModelAttribute("name") String name){
		producer.produceMsg(name,user.getExchange(),user.getRoutingKey());
		ModelAndView mv = new ModelAndView("sent");
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
	@ResponseBody
	public String consumeMsg() {
		
		return consumer.recievedMessage();
		
	}
}


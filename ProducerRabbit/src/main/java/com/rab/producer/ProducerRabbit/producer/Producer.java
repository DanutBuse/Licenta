package com.rab.producer.ProducerRabbit.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

@Component
public class Producer {
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	public void produceMsg(String msg,String EXCHANGE_NAME,String ROUTING_KEY){
		amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, msg);
		System.out.println("Send msg = " + msg);
		
	}
}


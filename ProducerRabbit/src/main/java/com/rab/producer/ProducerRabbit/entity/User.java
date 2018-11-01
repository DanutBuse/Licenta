package com.rab.producer.ProducerRabbit.entity;

import org.springframework.stereotype.Component;

@Component
public class User {
	
	String queue;
	String exchange;
	String routingKey;
	
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
}

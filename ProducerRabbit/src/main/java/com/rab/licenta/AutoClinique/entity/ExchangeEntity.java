package com.rab.licenta.AutoClinique.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Exchanges")
public class ExchangeEntity{

	

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="name")
	String exchange;

	@Column(name="routingKey")
	String routingKey;
	
	@OneToOne(mappedBy ="exchange")
	User sender;
	
	public ExchangeEntity() {
		super();
	}
	public ExchangeEntity(String exchange, String routingKey) {
		super();
		this.exchange = exchange;
		this.routingKey = routingKey;
	}
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getExchangeName() {
		return exchange;
	}


	public void setExchangeName(String exchange) {
		this.exchange = exchange;
	}


	public String getRoutingKey() {
		return routingKey;
	}


	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}


	public User getSender() {
		return sender;
	}


	public void setSender(User sender) {
		this.sender = sender;
	}


}

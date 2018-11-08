package com.rab.producer.ProducerRabbit.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;

	@Column(name="username")
	String username;
	@Column(name="password")
	String password;
	@Column(name="tip")
	String tipUtilizator;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="exchange")
	ExchangeEntity exchange;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="queue")
	QueueEntity queue;

	
	public User() {
		
	}
	public User(String username, String password, String tipUtilizator, ExchangeEntity exchange,
			QueueEntity queue) {
		super();
		this.username = username;
		this.password = password;
		this.tipUtilizator = tipUtilizator;
		this.exchange = exchange;
		this.queue = queue;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipUtilizator() {
		return tipUtilizator;
	}

	public void setTipUtilizator(String tipUtilizator) {
		this.tipUtilizator = tipUtilizator;
	}

	public ExchangeEntity getExchange() {
		return exchange;
	}

	public void setExchange(ExchangeEntity exchange) {
		this.exchange = exchange;
		this.exchange.setSender(this);
	}

	public QueueEntity getQueue() {
		return queue;
	}

	public void setQueue(QueueEntity queue) {
		this.queue = queue;
		this.queue.setReceiver(this);
	}
	
	
}

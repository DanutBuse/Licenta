package com.rab.licenta.AutoClinique.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Queues")
public class QueueEntity {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="name")
	String queueName;
	
	@OneToOne(mappedBy ="queue")
	User receiver;

	public QueueEntity() {
		super();
	}
	public QueueEntity(String queue) {
		super();
		
		this.queueName = queue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueue(String queue) {
		this.queueName = queue;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
}

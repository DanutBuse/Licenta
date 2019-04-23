package com.rab.producer.ProducerRabbit.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Messages")
public class MessageEntity {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@OneToOne
	@JoinColumn(name="masina")
	CarEntity masina;
	
	@OneToMany(mappedBy="mesaj")
	List<OfertaEntity> oferte;
	
	@Column(name="Description")
	String descriere;
	
	@Column(name="Sent")
	boolean sent;
	
	@Column(name="Received")
	boolean received;
	
	@Column(name="SentDate")
	@Temporal(TemporalType.TIMESTAMP)
	Date sentDate;
	
	@Column(name="ReceivedDate")
	@Temporal(TemporalType.TIMESTAMP)
	Date receivedDate;

	@ManyToOne
	@JoinColumn(name="sender")
	User sender;
	
	@ManyToOne
	@JoinColumn(name="receiver")
	User receiver;

	public MessageEntity() {
		
	}
	public MessageEntity(CarEntity data, User sender, User receiver, List<OfertaEntity> oferte, String descriere,
						boolean sent, boolean received) {
		super();
		this.masina = data;
		this.sender = sender;
		this.receiver = receiver;
		this.oferte = oferte;
		this.descriere = descriere;
		this.sent = sent;
		this.received = received;
	}
	

	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date date) {
		receivedDate = date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CarEntity getMasina() {
		return masina;
	}

	public void setMasina(CarEntity masina) {
		this.masina = masina;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public List<OfertaEntity> getOferta() {
		return oferte;
	}
	public void setOferte(List<OfertaEntity> oferte) {
		this.oferte = oferte;
	}
	public String getDescriere() {
		return descriere;
	}
	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}

}

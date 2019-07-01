package com.rab.licenta.AutoClinique.entity;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Messages")
public class MessageEntity {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@ManyToOne
	@JoinColumn(name="masina")
	CarEntity masina;
	
	@OneToMany(mappedBy="mesaj")
	List<OfferEntity> oferte;
	
	@Column(name="Description")
	String descriere;
	
	@Column(name="Conversation", length = 65535, columnDefinition="TEXT")
	@Type(type="text")
	String conversatie;
	
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
	public MessageEntity(CarEntity data, User sender, User receiver, List<OfferEntity> oferte, String descriere,
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

	public String getConversatie() {
		return conversatie;
	}
	public void setConversatie(String conversatie) {
		this.conversatie = conversatie;
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
	public List<OfferEntity> getOferte() {
		return oferte;
	}
	public void setOferte(List<OfferEntity> oferte) {
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

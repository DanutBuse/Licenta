package com.rab.producer.ProducerRabbit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Cars")
public class CarEntity{

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;

	@Column(name="marca")
	String marca;
	
	@Column(name="tip")
	String tip;
	
	@Column(name="VIN")
	String vin;
	
	@Column(name="An")
	Integer an;
	
	@ManyToOne
	@JoinColumn(name="client")
	User client;
	
	@OneToOne(mappedBy ="masina")
	MessageEntity mesaj;

	public CarEntity() {
		
	}
	
	public CarEntity(String marca, String tip, User client, String vin, Integer an, MessageEntity message) {
		super();
		this.marca = marca;
		this.tip = tip;
		this.client = client;
		this.vin = vin;
		this.an = an;
		this.mesaj = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVin() {
		return vin;
	}

	public Integer getAn() {
		return an;
	}

	public void setAn(Integer an) {
		this.an = an;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public void setMessage(MessageEntity message) {
		this.mesaj = message;
		
	}
	
	
}

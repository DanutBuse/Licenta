package com.rab.producer.ProducerRabbit.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Cars")
public class CarEntity{

	@Id
	@Column(name="VIN")
	@Size(max = 60)
	String vin;
	
	@Column(name="marca")
	String marca;
	
	@Column(name="tip")
	String tip;
	
	@Column(name="An")
	Integer an;
	
	@ManyToOne
	@JoinColumn(name="client")
	User client;
	
	@OneToMany(mappedBy ="masina")
	Set<MessageEntity> mesaje = new HashSet<>();

	public CarEntity() {
		
	}
	
	public CarEntity(String marca, String tip, User client, String vin, Integer an, Set<MessageEntity> message) {
		super();
		this.marca = marca;
		this.tip = tip;
		this.client = client;
		this.vin = vin;
		this.an = an;
		this.mesaje = message;
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

	public Set<MessageEntity> getMesaj() {
		return mesaje;
	}

	public void setMesaj(Set<MessageEntity> mesaj) {
		this.mesaje = mesaj;
	}
	
	
}

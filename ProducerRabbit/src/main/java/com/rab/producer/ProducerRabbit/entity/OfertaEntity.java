package com.rab.producer.ProducerRabbit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Oferte")
public class OfertaEntity{

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;

	@Column(name="producator")
	String producator;
	
	@Column(name="numepiesa")
	String numePiesa;
	
	@Column(name="pret")
	Integer pret;

	@ManyToOne
	@JoinColumn(name="mesaj")
	MessageEntity mesaj;
	
	public OfertaEntity() {
		
	}
	
	public OfertaEntity(String numePiesa, String producator, Integer pret, MessageEntity mesaj) {
		super();
		this.numePiesa = numePiesa;
		this.producator = producator;
		this.pret = pret;
		this.mesaj = mesaj;
	}

	public MessageEntity getMesaj() {
		return mesaj;
	}

	public void setMesaj(MessageEntity mesaj) {
		this.mesaj = mesaj;
	}

	public String getNumePiesa() {
		return numePiesa;
	}

	public void setNumePiesa(String numePiesa) {
		this.numePiesa = numePiesa;
	}

	public String getProducator() {
		return producator;
	}

	public void setProducator(String producator) {
		this.producator = producator;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPret() {
		return pret;
	}

	public void setPret(Integer pret) {
		this.pret = pret;
	}
	
	
}

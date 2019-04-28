package com.rab.producer.ProducerRabbit.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageWrapperDTO{
	
	private ArrayList<OfertaDTO> listaOferte;
	private String idMesaj;
	private String idCar;
	private CarDTO car;
	private String descriere;
	private String senderName;
	
	public MessageWrapperDTO(ArrayList<OfertaDTO> listaOferte, String idMesaj,CarDTO car,
							String idCar, String descriereCurenta, String senderName) {
		super();
		this.listaOferte = listaOferte;
		this.idMesaj = idMesaj;
		this.idCar = idCar;
		this.car = car;
		this.descriere = descriereCurenta;
		this.senderName = senderName;
	}
	
	public String getIdCar() {
		return idCar;
	}

	public void setIdCar(String idCar) {
		this.idCar = idCar;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public List<OfertaDTO> getListaOferte() {
		return listaOferte;
	}
	
	public CarDTO getCar() {
		return car;
	}
	public void setCar(CarDTO car) {
		this.car = car;
	}
	public void setListaOferte(ArrayList<OfertaDTO> listaOferte) {
		this.listaOferte = listaOferte;
	}
	public String getIdMesaj() {
		return idMesaj;
	}
	public void setIdMesaj(String idMesaj) {
		this.idMesaj = idMesaj;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
	
	
}
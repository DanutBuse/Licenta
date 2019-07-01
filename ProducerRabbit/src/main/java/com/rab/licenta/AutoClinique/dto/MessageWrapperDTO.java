package com.rab.licenta.AutoClinique.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageWrapperDTO{
	
	private ArrayList<OfferDTO> listaOferte;
	private String idMesaj;
	private String idCar;
	private CarDTO car;
	private String descriere;
	private String senderName;
	private String conversatie;
	
	public MessageWrapperDTO(ArrayList<OfferDTO> listaOferte, String idMesaj,CarDTO car,
							String idCar, String descriereCurenta, String senderName, String conversatie) {
		super();
		this.listaOferte = listaOferte;
		this.idMesaj = idMesaj;
		this.idCar = idCar;
		this.car = car;
		this.descriere = descriereCurenta;
		this.conversatie = conversatie;
		this.senderName = senderName;
	}
	
	public String getConversatie() {
		return conversatie;
	}

	public void setConversatie(String conversatie) {
		this.conversatie = conversatie;
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

	public List<OfferDTO> getListaOferte() {
		return listaOferte;
	}
	
	public CarDTO getCar() {
		return car;
	}
	public void setCar(CarDTO car) {
		this.car = car;
	}
	public void setListaOferte(ArrayList<OfferDTO> listaOferte) {
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

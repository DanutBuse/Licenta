package com.rab.producer.ProducerRabbit.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.SerializationUtils;



public class SupportReplyDTO implements Serializable{


	private static final long serialVersionUID = 3429963177037524832L;
	
	private ArrayList<OfertaDTO> listaOferte;
	private String idMesaj;
	private String descriereSuplimentara;
	private CarDTO car;
	
	
	public SupportReplyDTO(ArrayList<OfertaDTO> listaOferte, String idMesaj,CarDTO car, String descriereSuplimentara) {
		super();
		this.listaOferte = listaOferte;
		this.idMesaj = idMesaj;
		this.descriereSuplimentara = descriereSuplimentara;
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
	public String getDescriereSuplimentara() {
		return descriereSuplimentara;
	}
	public void setDescriereSuplimentara(String descriereSuplimentara) {
		this.descriereSuplimentara = descriereSuplimentara;
	}
	
	public byte[] getBytes() {
		return SerializationUtils.serialize(this);
    }

    public static SupportReplyDTO fromBytes(byte[] body) {
    	return (SupportReplyDTO) SerializationUtils.deserialize(body);
    }
	
}

package com.rab.licenta.AutoClinique.dto;

public class OfferDTO{
	
	private String numePiesa;
	private String producator;
	private Integer pret;
	
	public OfferDTO() {
		
	}
	
	public OfferDTO(String numePiesa, String producator, Integer pret) {
		super();
		this.numePiesa = numePiesa;
		this.producator = producator;
		this.pret = pret;
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

	public Integer getPret() {
		return pret;
	}

	public void setPret(Integer pret) {
		this.pret = pret;
	}

}

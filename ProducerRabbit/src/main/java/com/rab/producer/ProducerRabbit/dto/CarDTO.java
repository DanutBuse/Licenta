package com.rab.producer.ProducerRabbit.dto;

public class CarDTO{

	String marca;
	
	String tip;
	
	String vin;
	
	int an;

	public CarDTO() {
		
	}
	
	public CarDTO(String marca, String tip, String vin, int an) {
		super();
		this.marca = marca;
		this.tip = tip;
		this.vin = vin;
		this.an = an;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMarca() {
		return marca;
	}
	
	public int getAn() {
		return an;
	}

	public void setAn(int an) {
		this.an = an;
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
	
}

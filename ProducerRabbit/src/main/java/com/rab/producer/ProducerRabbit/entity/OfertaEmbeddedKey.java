package com.rab.producer.ProducerRabbit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class OfertaEmbeddedKey implements Serializable{

	@Size(max = 20)
	String numePiesa;

	@Size(max = 20)
	String producator;
	
	public OfertaEmbeddedKey() {
		
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfertaEmbeddedKey that = (OfertaEmbeddedKey) o;

        if (!numePiesa.equals(that.numePiesa) || !producator.equals(that.producator)) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = numePiesa.hashCode();
        result = 31 * result + producator.hashCode();
        return result;
    }
}

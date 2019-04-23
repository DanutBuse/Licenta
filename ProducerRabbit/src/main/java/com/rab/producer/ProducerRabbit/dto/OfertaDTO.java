package com.rab.producer.ProducerRabbit.dto;

import java.io.Serializable;

import org.springframework.util.SerializationUtils;


public class OfertaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String numePiesa;
	private String producator;
	private Integer pret;
	
	public OfertaDTO() {
		
	}
	
	public OfertaDTO(String numePiesa, String producator, Integer pret) {
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

	public byte[] getBytes() {
//        byte[]bytes;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try{
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(this);
//            oos.flush();
//            oos.reset();
//            bytes = baos.toByteArray();
//            oos.close();
//            baos.close();
//        } catch(IOException e){
//            bytes = new byte[] {};
//        }
//        return bytes;
		return SerializationUtils.serialize(this);
    }

    public static OfertaDTO fromBytes(byte[] body) {
//    	OfertaDTO obj = null;
//        try {
//            ByteArrayInputStream bis = new ByteArrayInputStream(body);
//            ObjectInputStream ois = new ObjectInputStream(bis);
//            obj = (OfertaDTO) ois.readObject();
//            ois.close();
//            bis.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        return obj;
    	return (OfertaDTO) SerializationUtils.deserialize(body);
    }
    
}

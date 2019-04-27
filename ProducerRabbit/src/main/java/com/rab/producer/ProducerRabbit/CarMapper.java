package com.rab.producer.ProducerRabbit;

import com.rab.producer.ProducerRabbit.dto.CarDTO;
import com.rab.producer.ProducerRabbit.entity.CarEntity;
import com.rab.producer.ProducerRabbit.entity.User;

public class CarMapper {
	
	public static CarDTO toDTO(CarEntity carEntity) {
		
		return new CarDTO(carEntity.getMarca(), carEntity.getTip(), carEntity.getVin(), carEntity.getAn());
		
	}
	
	public static CarEntity toEntity(CarDTO carDTO) {
		return new CarEntity(carDTO.getMarca(), carDTO.getTip(), new User(), carDTO.getVin(), carDTO.getAn(), null);
	}
	
}

package com.rab.licenta.AutoClinique;

import com.rab.licenta.AutoClinique.dto.CarDTO;
import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.User;

public class CarMapper {
	
	public static CarDTO toDTO(CarEntity carEntity) {
		
		return new CarDTO(carEntity.getMarca(), carEntity.getTip(), carEntity.getVin(), carEntity.getAn());
		
	}
	
	public static CarEntity toEntity(CarDTO carDTO) {
		return new CarEntity(carDTO.getMarca(), carDTO.getTip(), new User(), carDTO.getVin(), carDTO.getAn(), null);
	}
	
}

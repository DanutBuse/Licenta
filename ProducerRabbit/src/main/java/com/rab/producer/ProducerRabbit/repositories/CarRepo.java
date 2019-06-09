package com.rab.producer.ProducerRabbit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rab.producer.ProducerRabbit.entity.CarEntity;

@Repository
public interface CarRepo extends JpaRepository<CarEntity,String>{

	@Query("SELECT cars "
		 + "FROM CarEntity cars "
		 + "WHERE cars.marca = ?1 AND cars.tip = ?2 AND cars.an = ?3")
	public List<CarEntity> getCarsByTypeYear(String marca, String tip, int parseInt);

}

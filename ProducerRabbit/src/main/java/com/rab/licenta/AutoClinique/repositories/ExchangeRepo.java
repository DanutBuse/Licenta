package com.rab.licenta.AutoClinique.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rab.licenta.AutoClinique.entity.ExchangeEntity;
import com.rab.licenta.AutoClinique.entity.User;

@Repository
public interface ExchangeRepo extends JpaRepository<ExchangeEntity,Integer> {

	@Query("SELECT e FROM ExchangeEntity e WHERE e.exchange = ?1")
	public ExchangeEntity getByName(String name);
}
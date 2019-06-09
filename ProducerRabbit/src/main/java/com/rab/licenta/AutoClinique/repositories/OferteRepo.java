package com.rab.licenta.AutoClinique.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab.licenta.AutoClinique.entity.OfertaEntity;

@Repository
public interface OferteRepo extends JpaRepository<OfertaEntity,Integer>{

}

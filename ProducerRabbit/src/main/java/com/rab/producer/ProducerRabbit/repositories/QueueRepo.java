package com.rab.producer.ProducerRabbit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab.producer.ProducerRabbit.entity.QueueEntity;

@Repository
public interface QueueRepo extends JpaRepository<QueueEntity,Integer>{

}

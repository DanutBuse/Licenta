package com.rab.producer.ProducerRabbit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rab.producer.ProducerRabbit.entity.QueueEntity;

@Repository
public interface QueueRepo extends JpaRepository<QueueEntity,Integer>{
	
	@Query("SELECT q.queueName FROM QueueEntity q WHERE q.receiver.tipUtilizator = 'support' AND q.receiver.available = true")
	public List<String> getSupportQueues();

}

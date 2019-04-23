package com.rab.producer.ProducerRabbit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rab.producer.ProducerRabbit.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User getByName(String username);
	
	@Query("SELECT u FROM User u WHERE u.queue.queueName = ?1")
	public User getByQueueName(String queueName);
}

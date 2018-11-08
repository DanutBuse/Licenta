package com.rab.producer.ProducerRabbit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab.producer.ProducerRabbit.entity.ExchangeEntity;
import com.rab.producer.ProducerRabbit.entity.QueueEntity;
import com.rab.producer.ProducerRabbit.entity.User;
import com.rab.producer.ProducerRabbit.repositories.ExchangeRepo;
import com.rab.producer.ProducerRabbit.repositories.QueueRepo;
import com.rab.producer.ProducerRabbit.repositories.UserRepo;

@Service
public class DbService {
	
	@Autowired
	ExchangeRepo exchangeRepo;
	@Autowired
	QueueRepo queueRepo;
	@Autowired
	UserRepo userRepo;
	
	public void insertUser(User user) {
		userRepo.save(user);
	}
	public void insertQueue(QueueEntity queue) {
		queueRepo.save(queue);
	}
	public void insertExchange(ExchangeEntity exchange) {
		exchangeRepo.save(exchange);
	}
	public User getUserById(Integer id) {
		return userRepo.findById(id).get();
	}
	
}

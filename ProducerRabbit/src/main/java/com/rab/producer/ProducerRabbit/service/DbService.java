package com.rab.producer.ProducerRabbit.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
	public User getUserByName(String username) {
		return userRepo.getByName(username);
	}
	public void setUserQueueExchange(User u) {
		ExchangeEntity exchangeEntity = new ExchangeEntity("from"+u.getUsername(),"jsa.rountingkey");
		QueueEntity queueEntity = new QueueEntity("to"+u.getUsername());
		u.setExchange(exchangeEntity);
		u.setQueue(queueEntity);
	}
	public void setUserCookie(User u) {
		
	}
	public boolean check(User user, String pass) {
		return user.getPassword().equals(pass);
	}
	
}

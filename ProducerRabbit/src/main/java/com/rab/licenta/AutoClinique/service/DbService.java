package com.rab.licenta.AutoClinique.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab.licenta.AutoClinique.entity.CarEntity;
import com.rab.licenta.AutoClinique.entity.ExchangeEntity;
import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.QueueEntity;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.repositories.CarRepo;
import com.rab.licenta.AutoClinique.repositories.ExchangeRepo;
import com.rab.licenta.AutoClinique.repositories.MessageRepo;
import com.rab.licenta.AutoClinique.repositories.OferteRepo;
import com.rab.licenta.AutoClinique.repositories.QueueRepo;
import com.rab.licenta.AutoClinique.repositories.UserRepo;

@Service
public class DbService {
	
	@Autowired
	ExchangeRepo exchangeRepo;
	
	@Autowired
	QueueRepo queueRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	MessageRepo messageRepo;
	
	@Autowired
	CarRepo carRepo;
	
	@Autowired
	OferteRepo oferteRepo;
	
	public DbService() {
		
	}
	
	public void insertUser(User user) {
		userRepo.save(user);
	}
	
	public void insertUser(String userName,String pass,String type,ExchangeEntity exchange,QueueEntity queue) {
		User u = new User(userName,pass,type,exchange,queue);
		userRepo.save(u);
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
		ExchangeEntity exchangeEntity = new ExchangeEntity("from"+u.getUsername(),"jsa.rountingkey"+u.getUsername());
		QueueEntity queueEntity = new QueueEntity("to"+u.getUsername());
		u.setExchange(exchangeEntity);
		u.setQueue(queueEntity);
		userRepo.save(u);
	}
	
	public boolean check(User user, String pass) {
		return user.getPassword().equals(pass);
	}
	
	public boolean check(String userName) {
		return userRepo.getByName(userName) != null;
	}

	public void insertMessage(MessageEntity currentMessage) {
		messageRepo.save(currentMessage);
		
	}

	public List<MessageEntity> messagesByReceiver(User loggedUser) {
		return messageRepo.getMessagesByReceiver(loggedUser);
	}

	public List<MessageEntity> messagesBySender(User loggedUser) {
		return messageRepo.getMessagesBySender(loggedUser);
	}

	public void deleteMessage(String id) {
		messageRepo.deleteById(Integer.parseInt(id));
		
	}

	public void updateMessages(List<MessageEntity> messages) {

		messages.stream().forEach( (mes) -> {  
			mes.setSender(this.getUserByName(mes.getSender().getUsername()));
			this.insertMessage(mes); 
    	});
		
	}

	public List<String> getSuportsQueues() {
		return queueRepo.getSupportQueues();
	}

	public User getUserByQueueName(String lowestMessagesQueue) {
		return userRepo.getByQueueName(lowestMessagesQueue);
	}

	public void insertCar(CarEntity car) {
		carRepo.save(car);
		
	}

	public List<MessageEntity> sentMessagesBy(User loggedUser) {
		return messageRepo.getSentMessagesBy(loggedUser);
	}

	public void insertOrUpdateCars(List<MessageEntity> messages) {
		
		messages.stream().forEach( (mes) -> {
			mes.getMasina().setClient(this.getUserByName(mes.getMasina().getClient().getUsername()));
			
			if(!this.masinaExists(mes.getMasina())) 
				this.insertCar(mes.getMasina()); 
    	});
		
	}

	public MessageEntity getMessageById(int parseInt) {
		return messageRepo.getOne(parseInt);
	}

	public void addOferte(MessageEntity m) {
		
		m.getOferte().forEach( o -> oferteRepo.save(o));
	}

	public CarEntity getMasinaById(String id) {
		return carRepo.getOne(id);
	}

	public boolean masinaExists(CarEntity car) {
		return carRepo.existsById(car.getVin());
	}

	public List<CarEntity> getCarsByTypeYear(String marca, String tip, String an) {
		return carRepo.getCarsByTypeYear(marca, tip, Integer.parseInt(an));
	}
}

package com.rab.licenta.AutoClinique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rab.licenta.AutoClinique.entity.MessageEntity;
import com.rab.licenta.AutoClinique.entity.User;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity,Integer>{
	
	@Query("SELECT mes FROM MessageEntity mes WHERE mes.receiver = ?1")
	public List<MessageEntity> getMessagesByReceiver(User receiver);

	@Query("SELECT mes FROM MessageEntity mes WHERE mes.sender = ?1")
	public List<MessageEntity> getMessagesBySender(User loggedUser);

	@Query("SELECT mes FROM MessageEntity mes WHERE mes.sender = ?1")
	public List<MessageEntity> getSentMessagesBy(User loggedUser);

	@Query("SELECT mes FROM MessageEntity mes WHERE mes.masina.vin = ?1")
	public MessageEntity alreadyExists(String vin);

}

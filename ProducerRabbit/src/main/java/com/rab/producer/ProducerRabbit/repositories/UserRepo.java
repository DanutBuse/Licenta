package com.rab.producer.ProducerRabbit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab.producer.ProducerRabbit.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

}

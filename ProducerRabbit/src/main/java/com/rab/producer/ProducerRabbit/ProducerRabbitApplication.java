package com.rab.producer.ProducerRabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
public class ProducerRabbitApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProducerRabbitApplication.class);
	}
//	public static void main(String[] args) {
//		SpringApplication.run(ProducerRabbitApplication.class, args);
//	}
}
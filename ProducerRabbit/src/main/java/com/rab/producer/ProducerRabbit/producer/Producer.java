package com.rab.producer.ProducerRabbit.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

@Component
public class Producer {
	
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//	
//	@Value("${jsa.rabbitmq.exchange}")
//	private String exchange;
//	
//	@Value("${jsa.rabbitmq.routingkey}")
//	private String routingKey;
	
	public void produceMsg(String msg,String EXCHANGE_NAME,String QUEUE_NAME){
//		amqpTemplate.convertAndSend(exchange, routingKey, msg);
//		System.out.println("Send msg = " + msg);
		
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
		try {
			
		connection = factory.newConnection();
		
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "jsa.rountingkey");
        
        channel.basicPublish(EXCHANGE_NAME, "jsa.routingkey",true, 
        		MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        
        System.out.println(" [x] Sent '" + msg + "'");
        channel.close();
        connection.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


package com.rab.producer.ProducerRabbit.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class Consumer {
	
	String queueName;
	static String messaege = "";
	
	public Consumer() {
		
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	
	public void declareQueue(String queueName,String exchangeName,String rountingKey) {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
		try {
			
		connection = factory.newConnection();
		
        Channel channel = connection.createChannel();
        this.queueName=queueName;
        
        channel.exchangeDeclare(exchangeName, "direct",true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, rountingKey);
        
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
	//@RabbitListener(queues="${jsa.rabbitmq.queue}")
    public String recievedMessage() {
	//	  System.out.println("Recieved Message: " + msg);
    	
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection;
		try {
			
		connection = factory.newConnection();
		
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
              String message = new String(body, "UTF-8");
              Consumer.messaege = message;
              System.out.println(" [x] Received '" + message + "'");
            }
          };
          
        channel.basicConsume(queueName, true, consumer);
        channel.close();
        connection.close();
        String mesaj = Consumer.messaege;
        
        return mesaj;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    }
}

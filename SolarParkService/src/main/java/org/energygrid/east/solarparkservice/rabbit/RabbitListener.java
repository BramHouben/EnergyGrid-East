package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class RabbitListener extends RabbitConnection implements ApplicationRunner {

    private Logger logger = Logger.getLogger(RabbitListener.class.getName());

    private Object monitor;

    public RabbitListener() {
        super();
        monitor = new Object();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);

            DeliverCallback deliverCallback = (s, delivery) -> {
                AMQP.BasicProperties properties = new AMQP.BasicProperties()
                        .builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("received message: " + message);

                String replyMessage = "reply...";

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, replyMessage.getBytes());
            };

            channel.basicConsume(queueName, true, deliverCallback, s -> {});

            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    logger.log(Level.ALL, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }

        } catch (TimeoutException e) {
            logger.log(Level.ALL, e.getMessage());
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

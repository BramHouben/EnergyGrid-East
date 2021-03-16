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

@Component
public class RabbitListener extends RabbitConnection implements ApplicationRunner {

    private Object monitor;

    public RabbitListener() {
        super();
        monitor = new Object();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue_name, false, false, false, null);

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

            channel.basicConsume(queue_name, true, deliverCallback, s -> {});

            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

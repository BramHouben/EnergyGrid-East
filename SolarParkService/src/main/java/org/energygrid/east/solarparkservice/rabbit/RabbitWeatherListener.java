package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Profile("!test")
@Component
public class RabbitWeatherListener implements ApplicationRunner {

    private Logger logger = Logger.getLogger(RabbitWeatherListener.class.getName());
    private RabbitConfig rabbitConfig;
    private Object monitor;

    public RabbitWeatherListener() {
        rabbitConfig = RabbitConfig.getInstance();
        monitor = new Object();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Connection connection = RabbitConnection.getInstance().getConnection();

        if(connection == null) {
            logger.log(Level.ALL, "Failed to start Rabbit Connection");
            return;
        }

        try(Channel channel = connection.createChannel()) {
            rabbitConfig.declareQueue(channel);

            DeliverCallback deliverCallback = (s, delivery) -> {

                AMQP.BasicProperties properties = rabbitConfig.getProperties(delivery.getProperties().getCorrelationId());

                String message = rabbitConfig.getUTF8Message(delivery.getBody());


                logger.log(Level.ALL, "received message: " + message);

                String replyMessage = "reply...";

                rabbitConfig.sendMessage(channel, delivery.getProperties().getReplyTo(), properties, replyMessage);
            };

            rabbitConfig.startConsumer(channel, deliverCallback);

            startMonitor();

        } catch (TimeoutException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }

    private void startMonitor() {
        while(true) {
            synchronized (monitor){
                try{
                    monitor.wait();
                    break;
                }
                catch (InterruptedException e){
                    logger.log(Level.ALL, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

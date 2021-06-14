package org.energygrid.east.simulationsolarservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import org.energygrid.east.simulationsolarservice.rabbit.Consumer;
import org.energygrid.east.simulationsolarservice.rabbit.defaultconsumer.DefaultRabbitConsumer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolarParkConsumer implements Consumer<String> {

    private static final Logger logger = Logger.getLogger(SolarParkConsumer.class.getName());

    private final String queueName;
    private final String exchangeName;

    public SolarParkConsumer() {
        queueName = "simulation_solar_queue";
        exchangeName = "solarpark_exchange";
    }


    @Override
    public String consume(Channel channel) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueBind(queueName, exchangeName, "");

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            com.rabbitmq.client.Consumer consumer = new DefaultRabbitConsumer(channel, blockingQueue);

            channel.basicConsume(queueName, true, consumer);

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

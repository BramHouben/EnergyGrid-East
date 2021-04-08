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

    private final String queue_name;
    private final String exchange_name;

    public SolarParkConsumer() {
        queue_name = "simulation_solar_queue";
        exchange_name = "solarpark_exchange";
    }


    @Override
    public String consume(Channel channel) {
        try {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.exchangeDeclare(exchange_name, "direct", true);
            channel.queueBind(queue_name, exchange_name, "");

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            com.rabbitmq.client.Consumer consumer = new DefaultRabbitConsumer(channel, blockingQueue);

            channel.basicConsume(queue_name, true, consumer);

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

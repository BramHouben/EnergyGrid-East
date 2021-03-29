package org.energygrid.east.simulationservice.rabbit.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.energygrid.east.simulationservice.rabbit.Consumer;
import org.energygrid.east.simulationservice.rabbit.RabbitConfig;
import org.energygrid.east.simulationservice.rabbit.consumer.DefaultRabbitConsumer;
import org.energygrid.east.simulationservice.rabbit.consumer.DefaultRabbitRPCConsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherConsumer implements Consumer<String> {

    private static final Logger logger = Logger.getLogger(WeatherConsumer.class.getName());

    private final String QUEUE_NAME;
    private final String EXCHANGE_NAME;

    public WeatherConsumer() {
        QUEUE_NAME = "simulation_weather_queue";
        EXCHANGE_NAME = "weather_exchange";
    }

    @Override
    public String consume(Channel channel) {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            com.rabbitmq.client.Consumer consumer = new DefaultRabbitConsumer(channel, blockingQueue);

            channel.basicConsume(QUEUE_NAME, true, consumer);

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

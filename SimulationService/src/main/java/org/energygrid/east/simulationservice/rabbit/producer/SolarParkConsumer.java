package org.energygrid.east.simulationservice.rabbit.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.energygrid.east.simulationservice.rabbit.Consumer;
import org.energygrid.east.simulationservice.rabbit.RabbitConfig;
import org.energygrid.east.simulationservice.rabbit.consumer.DefaultRabbitRPCConsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolarParkConsumer implements Consumer<String> {

    private static final Logger logger = Logger.getLogger(SolarParkConsumer.class.getName());

    private String solarParkName;
    private final RabbitConfig rabbitConfig;
    private final String corrId;
    private final String REQUEST_QUEUE_NAME;

    public SolarParkConsumer(String solarParkName) {
        this.solarParkName = solarParkName;
        rabbitConfig = RabbitConfig.getInstance();
        corrId = UUID.randomUUID().toString();
        REQUEST_QUEUE_NAME = "solarparkservice_queue";
    }


    @Override
    public String consume(Channel channel) {
        try {
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties properties = rabbitConfig.getProperties(corrId, replyQueueName);

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            com.rabbitmq.client.Consumer consumer = new DefaultRabbitRPCConsumer(channel, blockingQueue, corrId);

            channel.basicConsume(replyQueueName, true, consumer);
            channel.basicPublish("", REQUEST_QUEUE_NAME, properties, solarParkName.getBytes());

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

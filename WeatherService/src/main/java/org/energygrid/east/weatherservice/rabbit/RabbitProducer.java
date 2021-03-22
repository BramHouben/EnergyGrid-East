package org.energygrid.east.weatherservice.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitProducer {

    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());
    private final String REQUEST_QUEUE_NAME = "solarpark_queue";
    private final RabbitConfig rabbitConfig;
    private final Connection connection;

    public RabbitProducer() {
        rabbitConfig = RabbitConfig.getInstance();
        connection = RabbitConnection.getInstance().getConnection();
    }

    public String getWeather() {

        if(connection == null) {
            logger.log(Level.ALL, "Unabel to get weather from solarparkservice, because connection is null");
            return null;
        }

        try (Channel channel = connection.createChannel()){
            String corrId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();

            AMQP.BasicProperties properties = rabbitConfig.getProperties(corrId, replyQueueName);

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if(properties.getCorrelationId().equals(corrId) && blockingQueue.offer(new String(body, StandardCharsets.UTF_8))) {
                        logger.log(Level.ALL, "Weather received");
                    }
                }
            };

            channel.basicConsume(replyQueueName, true, consumer);

            channel.basicPublish("", REQUEST_QUEUE_NAME, properties, "give me sunpercentage".getBytes());

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);

        } catch (IOException | TimeoutException | InterruptedException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

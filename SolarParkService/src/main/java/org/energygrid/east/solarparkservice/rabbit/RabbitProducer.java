package org.energygrid.east.solarparkservice.rabbit;

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

public class RabbitProducer extends RabbitConnection {

    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());

    private final String corrId;
    private String requestQueueName;
    private String message;

    public RabbitProducer(String requestQueueName, String message) {
        super();
        this.corrId = UUID.randomUUID().toString();
        this.requestQueueName = requestQueueName;
        this.message = message;
    }

    public String getMessage() {

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){

            String replyQueueName = channel.queueDeclare().getQueue();

            AMQP.BasicProperties properties = new AMQP.BasicProperties()
                    .builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    if(properties.getCorrelationId().equals(corrId) && !blockingQueue.offer(new String(body, StandardCharsets.UTF_8))){
                        logger.log(Level.ALL, "Error offering string to blocking queue");
                    }
                };
            };

            channel.basicConsume(replyQueueName, true, consumer);

            channel.basicPublish("", requestQueueName, properties, message.getBytes());

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);

        } catch (TimeoutException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
        } catch (InterruptedException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

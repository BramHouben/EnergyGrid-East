package org.energygrid.east.simulationservice.rabbit.defaultconsumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultRabbitRPCConsumer extends DefaultConsumer {

    private static final Logger logger = Logger.getLogger(DefaultRabbitRPCConsumer.class.getName());

    private BlockingQueue<String> blockingQueue;
    private String corrId;

    public DefaultRabbitRPCConsumer(Channel channel, BlockingQueue<String> blockingQueue, String corrId) {
        super(channel);
        this.blockingQueue = blockingQueue;
        this.corrId = corrId;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        if(properties.getCorrelationId().equals(corrId)){
            String message = new String(body, StandardCharsets.UTF_8);
            System.out.println("Message received: " + message);
            if(!blockingQueue.offer(message)){
                logger.log(Level.ALL, "Failed to offer message to queue");
            }
        }
    }
}

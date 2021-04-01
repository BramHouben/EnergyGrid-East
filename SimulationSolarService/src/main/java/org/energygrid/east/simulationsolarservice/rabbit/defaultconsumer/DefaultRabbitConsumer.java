package org.energygrid.east.simulationsolarservice.rabbit.defaultconsumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultRabbitConsumer extends DefaultConsumer {

    private final static Logger logger = Logger.getLogger(DefaultRabbitConsumer.class.getName());

    private final BlockingQueue<String> blockingQueue;

    public DefaultRabbitConsumer(Channel channel, BlockingQueue<String> blockingQueue) {
        super(channel);
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String weather = new String(body, StandardCharsets.UTF_8);
        if(!blockingQueue.offer(weather)) {
            logger.log(Level.ALL, "unable to offer weather to blockingqueue");
        }
    }
}

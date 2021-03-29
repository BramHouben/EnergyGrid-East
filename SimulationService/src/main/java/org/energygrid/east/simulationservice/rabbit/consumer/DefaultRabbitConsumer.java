package org.energygrid.east.simulationservice.rabbit.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

public class DefaultRabbitConsumer extends DefaultConsumer {

    private final BlockingQueue<String> blockingQueue;

    public DefaultRabbitConsumer(Channel channel, BlockingQueue<String> blockingQueue) {
        super(channel);
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String weather = new String(body, StandardCharsets.UTF_8);
        blockingQueue.offer(weather);
    }
}

package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitConfig {

    private static final RabbitConfig rabbitConfig = new RabbitConfig();
    private final String QUEUE_NAME = "solarpark_queue";

    private RabbitConfig() {

    }

    public static RabbitConfig getInstance() {
        return rabbitConfig;
    }

    public void declareQueue(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void startConsumer(Channel channel, DeliverCallback deliverCallback) throws IOException {
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, s -> {});
    }

    public AMQP.BasicProperties getProperties(String corrId) {
        return new AMQP.BasicProperties()
                .builder()
                .correlationId(corrId)
                .build();
    }

    public String getUTF8Message(byte[] body) {
        return new String(body, StandardCharsets.UTF_8);
    }

    public void sendMessage(Channel channel, String replyTo, AMQP.BasicProperties properties, String message) throws IOException {
        channel.basicPublish("", replyTo, properties, message.getBytes());
    }
}

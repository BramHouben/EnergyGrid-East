package org.energygrid.east.weatherservice.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class RabbitConfig {

    private static final RabbitConfig rabbitConfig = new RabbitConfig();

    private RabbitConfig() {

    }

    public static RabbitConfig getInstance() {
        return rabbitConfig;
    }

    public void startConsumer(Channel channel, DeliverCallback deliverCallback, String queueName) throws IOException {
        channel.basicConsume(queueName, true, deliverCallback, s -> {});
    }

    public AMQP.BasicProperties getProperties(String corrId, String replyName) {
        return new AMQP.BasicProperties()
                .builder()
                .correlationId(corrId)
                .replyTo(replyName)
                .build();
    }

}

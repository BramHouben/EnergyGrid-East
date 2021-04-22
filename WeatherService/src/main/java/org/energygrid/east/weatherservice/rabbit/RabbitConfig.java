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

    public AMQP.BasicProperties getProperties(String corrId) {
        return new AMQP.BasicProperties()
                .builder()
                .correlationId(corrId)
                .build();
    }
}

package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.AMQP;

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

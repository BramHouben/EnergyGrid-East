package org.energygrid.east.simulationservice.rabbit;

import com.rabbitmq.client.AMQP;

public class RabbitConfig {

    private static final RabbitConfig rabbitConfig = new RabbitConfig();

    private RabbitConfig() {

    }

    public static RabbitConfig getInstance() {
        return rabbitConfig;
    }

    public AMQP.BasicProperties getProperties(String corrId, String replyTo) {
        return new AMQP.BasicProperties()
                .builder()
                .correlationId(corrId)
                .replyTo(replyTo)
                .build();
    }
}

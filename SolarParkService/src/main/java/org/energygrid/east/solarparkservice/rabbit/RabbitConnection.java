package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.ConnectionFactory;

public abstract class RabbitConnection {

    protected final ConnectionFactory connectionFactory = new ConnectionFactory();
    protected final String queue_name = "solarparkservice_queue";

    public RabbitConnection() {
        connectionFactory.setHost("localhost");
    }
}

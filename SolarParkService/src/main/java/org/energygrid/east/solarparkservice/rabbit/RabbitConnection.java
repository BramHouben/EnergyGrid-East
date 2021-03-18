package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.ConnectionFactory;

public abstract class RabbitConnection {

    protected final ConnectionFactory connectionFactory = new ConnectionFactory();
    protected static final String QUEUE_NAME = "solarparkservice_queue";

    protected RabbitConnection() {
        connectionFactory.setHost("localhost");
    }
}
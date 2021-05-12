package org.energygrid.east.authenticationservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.energygrid.east.authenticationservice.rabbit.Consumer;
import org.energygrid.east.authenticationservice.rabbit.Monitor;
import org.energygrid.east.authenticationservice.rabbit.defaultconsumer.UpdateUserDeliverer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserConsumer implements Consumer {

    private static final Logger logger = Logger.getLogger(UpdateUserConsumer.class.getName());

    private final String queueName;
    private final String exchangeName;

    public UpdateUserConsumer() {
        queueName = "update_user_queue";
        exchangeName = "update_user_exchange";
    }

    @Override
    public Object consume(Channel channel) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueBind(queueName, exchangeName, "");

            DeliverCallback deliverCallback = new UpdateUserDeliverer();
            channel.basicConsume(queueName, true, deliverCallback, s -> {
            });

            Monitor monitor = new Monitor();
            monitor.start();

        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

        return null;
    }
}

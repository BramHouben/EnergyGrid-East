package org.energygrid.east.authenticationservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.energygrid.east.authenticationservice.rabbit.Consumer;
import org.energygrid.east.authenticationservice.rabbit.Monitor;
import org.energygrid.east.authenticationservice.rabbit.defaultconsumer.AddUserDeliverer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUserConsumer implements Consumer {

    private static final Logger logger = Logger.getLogger(AddUserConsumer.class.getName());

    private final String queueName;
    private final String exchangeName;

    public AddUserConsumer() {
        queueName = "add_user_queue";
        exchangeName = "add_user_exchange";
    }

    @Override
    public String consume(Channel channel) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueBind(queueName, exchangeName, "");

            DeliverCallback deliverCallback = new AddUserDeliverer();
            channel.basicConsume(queueName, true, deliverCallback, s -> {});

            var monitor = new Monitor();
            monitor.start();

        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }
}

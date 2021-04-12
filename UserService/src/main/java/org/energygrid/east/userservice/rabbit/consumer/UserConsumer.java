package org.energygrid.east.userservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.energygrid.east.userservice.rabbit.Consumer;
import org.energygrid.east.userservice.rabbit.Monitor;
import org.energygrid.east.userservice.rabbit.defaultconsumer.UserDeliverer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConsumer implements Consumer {

    private static final Logger logger = Logger.getLogger(UserConsumer.class.getName());

    private final String queue_name;
    private final String exchange_name;

    public UserConsumer() {
        queue_name = "register_user_queue";
        exchange_name = "user_exchange";
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.exchangeDeclare(exchange_name, "direct", true);
            channel.queueBind(queue_name, exchange_name, "");

            DeliverCallback deliverCallback = new UserDeliverer();

            channel.basicConsume(queue_name, true, deliverCallback, s -> {});

            Monitor monitor = new Monitor();
            monitor.start();

        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
            Thread.currentThread().interrupt();
        }

    }
}

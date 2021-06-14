package org.energygrid.east.userservice.rabbit.producer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.energygrid.east.userservice.model.rabbitmq.UserRabbitMq;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserProducer implements Producer {
    private UserRabbitMq user;
    private static final String EXCHANGE_NAME = "update_user_exchange";
    private static final Logger logger = Logger.getLogger(UpdateUserProducer.class.getName());

    public UpdateUserProducer(UserRabbitMq user) {
        this.user = user;
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            String json = new Gson().toJson(user);
            channel.basicPublish(EXCHANGE_NAME, "", null, json.getBytes());
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

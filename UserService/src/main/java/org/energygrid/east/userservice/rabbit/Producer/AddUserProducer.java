package org.energygrid.east.userservice.rabbit.Producer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.rabbitMq.UserRabbitMq;
import org.energygrid.east.userservice.rabbit.RabbitProducer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddUserProducer implements Producer {

    private UserRabbitMq user;
    private final String exchangeName = "add_user_exchange";
    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());

    public AddUserProducer(UserRabbitMq user) {
        this.user = user;
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchangeName, "direct", true);
            String json = new Gson().toJson(user);
            channel.basicPublish(exchangeName, "", null, json.getBytes());
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

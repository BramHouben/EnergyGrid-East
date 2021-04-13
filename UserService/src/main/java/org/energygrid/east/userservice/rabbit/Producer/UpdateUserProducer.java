package org.energygrid.east.userservice.rabbit.Producer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.energygrid.east.userservice.model.rabbitMq.UserRabbitMq;

import java.io.IOException;

public class UpdateUserProducer implements Producer {
    private UserRabbitMq user;
    private final String exchangeName = "update_user_exchange";

    public UpdateUserProducer(UserRabbitMq user) {
        this.user = user;
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchangeName, "direct", true);
            String json = new Gson().toJson(user);
            channel.basicPublish(exchangeName, "", null, json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO add logging
        }
    }
}

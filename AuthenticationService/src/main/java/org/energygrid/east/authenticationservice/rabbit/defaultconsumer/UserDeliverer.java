package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.charset.StandardCharsets;

public class UserDeliverer implements DeliverCallback {

    @Autowired
    private IUserService userService;

    private Gson gson = new Gson();

    @Override
    public void handle(String s, Delivery delivery) {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
        UserRabbitMq user = gson.fromJson(json, UserRabbitMq.class);
        userService.addUser(user);
    }
}

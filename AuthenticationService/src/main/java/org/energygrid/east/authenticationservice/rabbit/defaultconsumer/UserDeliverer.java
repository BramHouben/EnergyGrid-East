package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserDeliverer implements DeliverCallback {

    @Autowired
    private IUserService userService;

    private Gson gson = new Gson();

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);

        User user = gson.fromJson(json, User.class);

        userService.addUser(user.getEmail(), user.getPassword());
    }
}

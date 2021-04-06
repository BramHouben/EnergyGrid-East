package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.service.IAuthenticationService;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserDeliverer implements DeliverCallback {

    @Autowired
    public IUserService userService;

    private Gson gson = new Gson();

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);

        User user = gson.fromJson(json, User.class);

        userService.addUser(user.getEmail(), user.getPassword());
    }
}
package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.energygrid.east.authenticationservice.controller.ApplicationContextUtils;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UserDeliverer implements DeliverCallback {

    private ApplicationContext applicationContext;
    private IUserService userService;
    private final Gson gson = new Gson();

    public UserDeliverer() {
        applicationContext = ApplicationContextUtils.getCtx();
        userService = applicationContext.getBean(IUserService.class);
    }

    @Override
    public void handle(String s, Delivery delivery) {
        String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
        var user = gson.fromJson(json, UserRabbitMq.class);
        userService.addUser(user.getEmail(), user.getPassword());
    }
}
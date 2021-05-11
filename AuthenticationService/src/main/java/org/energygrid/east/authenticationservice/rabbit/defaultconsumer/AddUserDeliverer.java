package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.energygrid.east.authenticationservice.controller.ApplicationContextUtils;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AddUserDeliverer implements DeliverCallback {

    private ApplicationContext applicationContext;
    private IUserService userService;
    private final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger(AddUserDeliverer.class.getName());

    public AddUserDeliverer() {
        applicationContext = ApplicationContextUtils.getCtx();
        userService = applicationContext.getBean(IUserService.class);
    }

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            var user = gson.fromJson(json, UserRabbitMq.class);
            userService.addUser(user);
        }
        catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
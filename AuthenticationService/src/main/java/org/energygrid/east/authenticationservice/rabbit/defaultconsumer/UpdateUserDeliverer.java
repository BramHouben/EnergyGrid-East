package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.google.gson.Gson;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.rabbit.ApplicationContextUtils;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UpdateUserDeliverer implements DeliverCallback {

    private static final Logger logger = Logger.getLogger(UpdateUserDeliverer.class.getName());
    private final Gson gson = new Gson();

    @Autowired
    private final IUserService userService;

    public UpdateUserDeliverer() {
        logger.log(Level.INFO,"-------------------------------------------------------------------------------new implem");
        var applicationContext = ApplicationContextUtils.getCtx();
        userService = applicationContext.getBean(IUserService.class);
    }

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            var json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            var user = gson.fromJson(json, UserRabbitMq.class);
            userService.updateUser(user);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
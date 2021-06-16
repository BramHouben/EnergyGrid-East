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
public class DeleteUserDeliverer implements DeliverCallback {

    private static final Logger logger = Logger.getLogger(DeleteUserDeliverer.class.getName());
    private final Gson gson = new Gson();


    private final IUserService userService;

    public DeleteUserDeliverer() {
        logger.log(Level.INFO,"-------------------------------------------------------------------------------new implem");
        var applicationContext = ApplicationContextUtils.getCtx();
        userService = applicationContext.getBean(IUserService.class);
    }

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            logger.log(Level.INFO,"-------------------------------------------------------------------------------new implem");

            var json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            var user = gson.fromJson(json, UserRabbitMq.class);
            userService.deleteUser(user.getUuid());
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
package org.energygrid.east.authenticationservice.rabbit.executor;

import com.google.gson.Gson;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.service.IUserService;
import org.energygrid.east.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DeleteUserExecutor implements Runnable {

    private static final Logger logger = Logger.getLogger(String.valueOf(DeleteUserExecutor.class));
    private String json;
    private final Gson gson = new Gson();

    private UserService userService;


    @PostConstruct
    public void test(){
        userService = new UserService();
    }

    public DeleteUserExecutor(String json) {
        this.json = json;
    }

    @Override
    public void run() {
        var user = gson.fromJson(json, UserRabbitMq.class);
        logger.log(Level.INFO, "---------------NEW IMPLEMENTATION----------------");
        logger.log(Level.INFO, "Email: ", user.getEmail());
        try {
            userService.deleteUser(user.getUuid());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package org.energygrid.east.authenticationservice.rabbit.executor;

import com.google.gson.Gson;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.rabbit.ApplicationContextUtils;
import org.energygrid.east.authenticationservice.service.IUserService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteUserExecutor implements Runnable {

    private static final Logger logger = Logger.getLogger(String.valueOf(DeleteUserExecutor.class));
    private String json;
    private final Gson gson = new Gson();

    private IUserService userService;

    public DeleteUserExecutor(String json) {
        var applicationContext = ApplicationContextUtils.getCtx();
        userService = applicationContext.getBean(IUserService.class);
        this.json = json;
    }

    @Override
    public void run() {
        var user = gson.fromJson(json, UserRabbitMq.class);
        logger.log(Level.INFO, "---------------NEW IMPLEMENTATION----------------");
        try {
            userService.deleteUser(user.getUuid());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import jdk.jfr.Category;
import org.energygrid.east.authenticationservice.rabbit.executor.DeleteUserExecutor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DeleteUserDeliverer implements DeliverCallback {

    private static final Logger logger = Logger.getLogger(DeleteUserDeliverer.class.getName());
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void handle(String s, Delivery delivery) {
        try {
            logger.log(Level.INFO, "---------------NEW IMPLEMENTATION----------------");
            logger.log(Level.INFO, "string : " + s + ", body : " + delivery.getBody());
            var json = new String(delivery.getBody(), StandardCharsets.UTF_8);
            executorService.execute(new DeleteUserExecutor(json));

        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}
package org.energygrid.east.authenticationservice.rabbit.defaultconsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class UserDeliverer implements DeliverCallback {


    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        String user = new String(delivery.getBody(), StandardCharsets.UTF_8);

    }
}

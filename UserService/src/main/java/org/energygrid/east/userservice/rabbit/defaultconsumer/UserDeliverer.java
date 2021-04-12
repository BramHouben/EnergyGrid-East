package org.energygrid.east.userservice.rabbit.defaultconsumer;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserDeliverer implements DeliverCallback {


    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        String user = new String(delivery.getBody(), StandardCharsets.UTF_8);
    }
}

package org.energygrid.east.weatherservice.rabbit.deliverer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.energygrid.east.weatherservice.rabbit.RabbitConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WeatherDelivererCallBack implements DeliverCallback {

    private Channel channel;
    private RabbitConfig rabbitConfig;

    public WeatherDelivererCallBack(Channel channel) {
        this.channel = channel;
        rabbitConfig = RabbitConfig.getInstance();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        AMQP.BasicProperties properties = rabbitConfig.getProperties(delivery.getProperties().getCorrelationId());
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(message);
        channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, "45 graden".getBytes());
    }
}

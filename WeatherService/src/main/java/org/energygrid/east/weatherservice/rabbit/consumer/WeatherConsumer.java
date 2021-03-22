package org.energygrid.east.weatherservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.energygrid.east.weatherservice.rabbit.Consumer;
import org.energygrid.east.weatherservice.rabbit.Monitor;
import org.energygrid.east.weatherservice.rabbit.RabbitConfiguration;
import org.energygrid.east.weatherservice.rabbit.deliverer.WeatherDelivererCallBack;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherConsumer implements Consumer {

    private static final Logger logger = Logger.getLogger(WeatherConsumer.class.getName());

    private final String QUEUE_NAME;
    private final Object monitor;

    public WeatherConsumer() {
        QUEUE_NAME = "weatherservice_queue";
        monitor = new Object();
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = new WeatherDelivererCallBack(channel);
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, s -> {});

            Monitor monitor = new Monitor();
            monitor.start();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }


    }
}

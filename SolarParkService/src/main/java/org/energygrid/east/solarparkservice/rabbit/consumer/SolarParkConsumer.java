package org.energygrid.east.solarparkservice.rabbit.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.energygrid.east.solarparkservice.rabbit.Consumer;
import org.energygrid.east.solarparkservice.rabbit.Monitor;
import org.energygrid.east.solarparkservice.rabbit.deliverer.SolarParkDelivererCallBack;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolarParkConsumer implements Consumer {

    private static final Logger logger = Logger.getLogger(SolarParkConsumer.class.getName());

    private final String QUEUE_NAME;
    private final Object monitor;

    public SolarParkConsumer() {
        QUEUE_NAME = "solarparkservice_queue";
        monitor = new Object();
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = new SolarParkDelivererCallBack(channel);
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, s -> { });

            Monitor monitor = new Monitor();
            monitor.start();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

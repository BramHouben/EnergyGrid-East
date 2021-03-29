package org.energygrid.east.weatherservice.rabbit.timer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherTimer extends TimerTask {

    private Logger logger = Logger.getLogger(WeatherTimer.class.getName());

    private final Channel channel;
    private final String EXCHANGE_NAME;

    public WeatherTimer(Channel channel, String EXCHANGE_NAME) {
        this.channel = channel;
        this.EXCHANGE_NAME = EXCHANGE_NAME;
    }

    @Override
    public void run() {
        try {
            channel.basicPublish(EXCHANGE_NAME, "", null, "45 degrees".getBytes());
            System.out.println("WEATHER PUBLISHED");
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

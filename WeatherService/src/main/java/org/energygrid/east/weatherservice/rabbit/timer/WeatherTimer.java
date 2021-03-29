package org.energygrid.east.weatherservice.rabbit.timer;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherTimer extends TimerTask {

    private Logger logger = Logger.getLogger(WeatherTimer.class.getName());

    private final Channel channel;
    private final String exchange_name;

    public WeatherTimer(Channel channel, String exchange_name) {
        this.channel = channel;
        this.exchange_name = exchange_name;
    }

    @Override
    public void run() {
        try {
            channel.basicPublish(exchange_name, "", null, "45 degrees".getBytes());
            logger.log(Level.ALL, "weather published");
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

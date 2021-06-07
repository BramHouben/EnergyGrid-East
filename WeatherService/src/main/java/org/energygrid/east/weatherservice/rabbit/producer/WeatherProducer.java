package org.energygrid.east.weatherservice.rabbit.producer;

import com.rabbitmq.client.Channel;
import org.energygrid.east.weatherservice.rabbit.Monitor;
import org.energygrid.east.weatherservice.rabbit.Producer;
import org.energygrid.east.weatherservice.rabbit.timer.WeatherTimer;

import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherProducer implements Producer {

    private static final Logger logger = Logger.getLogger(WeatherProducer.class.getName());

    private final String exchangeName;

    public WeatherProducer() {
        exchangeName = "weather_exchange";
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchangeName, "direct", true);

            var weatherTimer = new WeatherTimer(channel, exchangeName);

            var timer = new Timer();
            timer.scheduleAtFixedRate(weatherTimer, 1000, 600000);

            var monitor = new Monitor();
            monitor.start();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

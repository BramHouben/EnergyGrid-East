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

    private final String EXCHANGE_NAME;

    public WeatherProducer() {
        EXCHANGE_NAME = "weather_exchange";
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            WeatherTimer weatherTimer = new WeatherTimer(channel, EXCHANGE_NAME);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(weatherTimer, 0, 10000);

            Monitor monitor = new Monitor();
            monitor.start();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

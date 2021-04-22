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

    private final String exchange_name;

    public WeatherProducer() {
        exchange_name = "weather_exchange";
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchange_name, "direct", true);

            WeatherTimer weatherTimer = new WeatherTimer(channel, exchange_name);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(weatherTimer, 1000, 600000);

            Monitor monitor = new Monitor();
            monitor.start();
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

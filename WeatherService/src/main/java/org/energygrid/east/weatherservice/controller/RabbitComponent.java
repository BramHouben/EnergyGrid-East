package org.energygrid.east.weatherservice.controller;

import org.energygrid.east.weatherservice.rabbit.RabbitConsumer;
import org.energygrid.east.weatherservice.rabbit.consumer.WeatherConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class RabbitComponent implements ApplicationRunner {

    public RabbitComponent() {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        RabbitConsumer rabbitConsumer = new RabbitConsumer();
        WeatherConsumer weatherConsumer = new WeatherConsumer();

        rabbitConsumer.consume(weatherConsumer);

    }
}

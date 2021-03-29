package org.energygrid.east.weatherservice.controller;

import org.energygrid.east.weatherservice.rabbit.RabbitProducer;
import org.energygrid.east.weatherservice.rabbit.producer.WeatherProducer;
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

        RabbitProducer rabbitProducer = new RabbitProducer();
        WeatherProducer weatherProducer = new WeatherProducer();

        rabbitProducer.produce(weatherProducer);

    }
}

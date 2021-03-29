package org.energygrid.east.solarparkservice.controller;

import org.energygrid.east.solarparkservice.rabbit.RabbitConsumer;
import org.energygrid.east.solarparkservice.rabbit.consumer.SolarParkConsumer;
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
        SolarParkConsumer solarParkConsumer = new SolarParkConsumer();

        rabbitConsumer.consume(solarParkConsumer);
    }
}

package org.energygrid.east.weatherservice.controller;

import org.energygrid.east.weatherservice.models.SolarTimer;
import org.energygrid.east.weatherservice.rabbit.RabbitProducer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class StartRabbitTimers implements ApplicationRunner {

    public StartRabbitTimers() {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        RabbitProducer rabbitProducer = new RabbitProducer();
        SolarTimer solarTimer = new SolarTimer(rabbitProducer);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(solarTimer, 0, 600000);

    }
}

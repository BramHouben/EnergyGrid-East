package org.energygrid.east.weatherservice.models;

import org.energygrid.east.weatherservice.rabbit.RabbitProducer;

import java.util.TimerTask;

public class SolarTimer extends TimerTask {

    private RabbitProducer rabbitProducer;

    public SolarTimer(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @Override
    public void run() {
        String solarWeather = rabbitProducer.getWeather();
        System.out.println("weather received: " + solarWeather);
    }
}

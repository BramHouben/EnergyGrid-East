package org.energygrid.east.solarparkservice.rabbit;

import java.util.UUID;


public class RabbitSolarSimulation extends RabbitConnection {

    private final String corrId;
    private final String requestQueueName;

    public RabbitSolarSimulation() {
        super();
        this.corrId = UUID.randomUUID().toString();
        this.requestQueueName = "weather_queue";
    }

    public String getWeather() {
        return null;
    }
}

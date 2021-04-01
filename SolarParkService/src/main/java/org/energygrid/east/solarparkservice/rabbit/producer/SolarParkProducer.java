package org.energygrid.east.solarparkservice.rabbit.producer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.energygrid.east.solarparkservice.model.dto.AddSolarParkDTO;
import org.energygrid.east.solarparkservice.model.dto.SimulationSolarDTO;
import org.energygrid.east.solarparkservice.rabbit.Producer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolarParkProducer implements Producer {

    private static final Logger logger = Logger.getLogger(SolarParkProducer.class.getName());

    private final String exchange_name;
    private final AddSolarParkDTO solarPark;

    public SolarParkProducer(AddSolarParkDTO solarPark) {
        exchange_name = "solarpark_exchange";
        this.solarPark = solarPark;
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchange_name, "direct", true);

            SimulationSolarDTO simulationSolarDTO = new SimulationSolarDTO(solarPark.getSolarParkName(), solarPark.getCountSonarPanels(), solarPark.getCoordinates());
            Gson gson = new Gson();

            channel.basicPublish(exchange_name, "", null, gson.toJson(simulationSolarDTO).getBytes());
            logger.log(Level.ALL, "solarparks published");
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

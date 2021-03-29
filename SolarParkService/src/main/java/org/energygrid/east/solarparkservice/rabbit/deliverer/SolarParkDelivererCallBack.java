package org.energygrid.east.solarparkservice.rabbit.deliverer;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.energygrid.east.solarparkservice.controller.ApplicationContextUtils;
import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.model.dto.SimulationSolarDTO;
import org.energygrid.east.solarparkservice.rabbit.RabbitConfig;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SolarParkDelivererCallBack implements DeliverCallback {

    private ISolarParkPower solarParkPower;
    private ApplicationContext applicationContext;
    private Channel channel;
    private RabbitConfig rabbitConfig;

    public SolarParkDelivererCallBack(Channel channel) {
        applicationContext = ApplicationContextUtils.getCtx();
        solarParkPower = applicationContext.getBean(ISolarParkPower.class);
        this.channel = channel;
        rabbitConfig = RabbitConfig.getInstance();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        AMQP.BasicProperties properties = rabbitConfig.getProperties(delivery.getProperties().getCorrelationId());
        String receivedSolarParkName = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(receivedSolarParkName);

        try{
            SolarPark solarPark = solarParkPower.getSolarParkByName(receivedSolarParkName);
            SimulationSolarDTO simulationSolarDTO = new SimulationSolarDTO(solarPark.getSolarParkName(), solarPark.getCountSonarPanels(), solarPark.getCoordinates());

            channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, new Gson().toJson(simulationSolarDTO).getBytes());
        }
        catch (SolarParkNotFoundException e){
            System.out.println("Solarpark with name: " + receivedSolarParkName + " not found");
        }
    }
}

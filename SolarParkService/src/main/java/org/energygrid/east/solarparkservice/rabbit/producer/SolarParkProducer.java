package org.energygrid.east.solarparkservice.rabbit.producer;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.energygrid.east.solarparkservice.controller.ApplicationContextUtils;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.rabbit.Producer;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolarParkProducer implements Producer {

    private static final Logger logger = Logger.getLogger(SolarParkProducer.class.getName());

    private final String exchangeName;
    private final ISolarParkPower solarParkPower;

    public SolarParkProducer() {
        exchangeName = "solarpark_exchange";
        ApplicationContext applicationContext = ApplicationContextUtils.getCtx();
        solarParkPower = applicationContext.getBean(ISolarParkPower.class);
    }

    @Override
    public void produce(Channel channel) {
        try {
            channel.exchangeDeclare(exchangeName, "direct", true);

            Gson gson = new Gson();
            List<SolarPark> solarParks = solarParkPower.getAll();

            channel.basicPublish(exchangeName, "", null, gson.toJson(solarParks).getBytes());
            logger.log(Level.ALL, "solarparks published");
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }
}

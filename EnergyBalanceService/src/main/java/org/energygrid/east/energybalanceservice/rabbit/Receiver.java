package org.energygrid.east.energybalanceservice.rabbit;

import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.IRabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {
    private static final java.util.logging.Logger logger = Logger.getLogger(Receiver.class.getName());

    @Autowired
    private IRabbitService rabbitService;

    public void receiveMessageWind(double message) {
        logger.log(Level.INFO, ()-> "Got message in receiver Wind" + message);
        rabbitService.addLatestWind(message);
    }

    public void receiveMessageSolar(String message) {
        logger.log(Level.INFO,  ()-> "Got message in receiver Solar" + message);
        rabbitService.addLatestSolar(message);
    }

    public void receiveMessageNuclear(String message) {
        logger.log(Level.INFO, ()->  "Got message in receiver Nuclear" + message);
        rabbitService.addLatestNuclear(message);
    }

    public void receiveMessageUsage(String message){
        logger.log(Level.INFO,()-> "Got message in receiver Usage"+ message);
        rabbitService.addLatestUsage(message);
    }
}

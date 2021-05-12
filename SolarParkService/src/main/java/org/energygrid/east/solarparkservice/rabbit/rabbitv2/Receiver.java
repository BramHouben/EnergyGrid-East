package org.energygrid.east.solarparkservice.rabbit.rabbitv2;

import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {
    private static final java.util.logging.Logger logger = Logger.getLogger(Receiver.class.getName());


    public Object receiveMessageParks(String message) {
        logger.log(Level.INFO, () -> "receiveMessageParks" + message);
        return "test";
    }


}
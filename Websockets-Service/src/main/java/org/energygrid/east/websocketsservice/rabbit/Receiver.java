package org.energygrid.east.websocketsservice.rabbit;

import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {

    private static final java.util.logging.Logger logger = Logger.getLogger(Receiver.class.getName());

    public void getBalance(String message) {
        logger.log(Level.INFO, message);
    }
}

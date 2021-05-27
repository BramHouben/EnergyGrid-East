package org.energygrid.east.websocketsservice.rabbit;

import org.energygrid.east.websocketsservice.models.EnergyBalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {

    @Autowired
    SimpMessagingTemplate template;

    private static final java.util.logging.Logger logger = Logger.getLogger(Receiver.class.getName());

    public void getBalance(String message) {
        logger.log(Level.INFO, message);
        listen(message);
    }

    public void listen(String message) {
        System.out.println("sending websocket message..");
        template.convertAndSend("/topic", message);
    }
}

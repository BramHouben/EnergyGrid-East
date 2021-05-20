package com.example.websocketsservice.rabbit;

import com.example.websocketsservice.models.EnergyBalanceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {

    @Autowired
    private SimpMessagingTemplate template;

    private static final Logger logger = Logger.getLogger(Receiver.class.getName());

     void test(String message)  {
        logger.log(Level.INFO,"test");

        //listen(String message / Object EnergyBalanceDTO)
    }

    private void listen(String message) {
        System.out.println("sending via websockets..");
        template.convertAndSend("/topic", message);
    }
}

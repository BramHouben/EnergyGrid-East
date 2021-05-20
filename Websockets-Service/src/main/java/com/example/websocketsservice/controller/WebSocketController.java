package com.example.websocketsservice.controller;

import com.example.websocketsservice.models.EnergyBalanceDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ws")
public class WebSocketController {

    @MessageMapping("/update/balance")
    @SendTo("/topic")
    public EnergyBalanceDTO updateProductionWind(@Payload EnergyBalanceDTO energyBalance) {
        return energyBalance;
    }
}

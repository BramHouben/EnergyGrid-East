package org.energygrid.east.websocketsservice.controller;


import org.energygrid.east.websocketsservice.models.EnergyBalanceDTO;
import org.energygrid.east.websocketsservice.models.SolarParkProductionViewModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ws")
public class WebSocketController {

    @MessageMapping("/update/balance")
    @SendTo("/topic")
    public EnergyBalanceDTO updateProductionWind(@Payload EnergyBalanceDTO energyBalance) {
        return energyBalance;
    }

    @MessageMapping("/update/overview")
    @SendTo("/topic-overview")
    public List<SolarParkProductionViewModel> updateProductionOverview(@Payload List<SolarParkProductionViewModel> solarParkProduction) {
        return solarParkProduction;
    }
}
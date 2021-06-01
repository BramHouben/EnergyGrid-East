package org.energygrid.east.websocketsservice.rabbit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.energygrid.east.websocketsservice.models.EnergyBalanceDTO;
import org.energygrid.east.websocketsservice.models.SolarParkProductionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Receiver {

    private static final java.util.logging.Logger logger = Logger.getLogger(Receiver.class.getName());
    @Autowired
    private SimpMessagingTemplate template;

    public void getBalance(String message) {
        logger.log(Level.INFO, message);
        try {
            var objectMapper = new ObjectMapper();
            var energyBalanceDTO = objectMapper.readValue(message, EnergyBalanceDTO.class);
            listen(energyBalanceDTO);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void receiveOverviewProduction(String message) {
        logger.log(Level.INFO, message);
        try {
            var objectMapper = new ObjectMapper();
            List<SolarParkProductionViewModel> solarParkProduction = objectMapper.readValue(message, new TypeReference<>() {
            });
            listenOverviewProduction(solarParkProduction);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void listen(EnergyBalanceDTO energyBalanceDTO) {
        logger.log(Level.INFO, "sending websocket message..");
        template.convertAndSend("/topic", energyBalanceDTO);
    }

    public void listenOverviewProduction(List<SolarParkProductionViewModel> solarParkProductionViewModel) {
        logger.log(Level.INFO, "sending websocket message..");
        template.convertAndSend("/topic-overview", solarParkProductionViewModel);
    }
}

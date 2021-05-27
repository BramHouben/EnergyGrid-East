package org.energygrid.east.energybalanceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.model.EnergyBalanceDTO;
import org.energygrid.east.energybalanceservice.model.Type;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EnergyService implements IEnergyService {

    private static final java.util.logging.Logger logger = Logger.getLogger(EnergyService.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Autowired
    private EnergyBalanceRepo energyBalanceRepo;

    @Override
    public EnergyBalance getLatestBalance() {
        logger.log(Level.INFO, () -> "latest balance called");

        return energyBalanceRepo.findFirstByOrderByTimeDesc();

    }

    @Scheduled(fixedDelay = 60000, initialDelay = 20000)
    public void updateNewestBalance() {
        try {
            logger.log(Level.INFO, () -> "update NewestBalance called");
            long usagePerMinute = 190000;
            //long latestSolar = energyBalanceStoreRepo.findFirstByType(Type.SOLAR).getProduction();
            long latestNuclear = 6300;
            //long latestWind = energyBalanceStoreRepo.findFirstByType(Type.WIND).getProduction();
            var max = 17000;
            var min = 10000;
            Random random = new Random();
            var solar = random.nextInt(max - min + 1) + min;
            var wind = random.nextInt(max - min + 1) + min;

            long total = +latestNuclear + solar + wind;
            long leverage = 156150;
            total += leverage;

            double balance = ((float) total / usagePerMinute) * 100;
            //per minute

            var energyBalance = new EnergyBalance(UUID.randomUUID(), usagePerMinute, total, balance, LocalDateTime.now(ZoneOffset.UTC));
            var energyBalanceDTO = new EnergyBalanceDTO(energyBalance.getConsume(), energyBalance.getProduction(), energyBalance.getBalance(), energyBalance.getTime().toString());

            var message = objectMapper.writeValueAsString(energyBalanceDTO);

            rabbitTemplate.convertAndSend("websockets", "websocket.balance.update", message);
            energyBalanceRepo.save(energyBalance);
        }
        catch(Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}

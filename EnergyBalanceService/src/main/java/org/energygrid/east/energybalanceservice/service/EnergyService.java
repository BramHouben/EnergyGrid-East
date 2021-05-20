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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EnergyService implements IEnergyService {

    private static final java.util.logging.Logger logger = Logger.getLogger(EnergyService.class.getName());
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;
    @Autowired
    private EnergyBalanceRepo energyBalanceRepo;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public EnergyBalance getLatestBalance() {
        logger.log(Level.INFO, () -> "latest balance called");

        return energyBalanceRepo.findFirstByOrderByTimeDesc();

    }

    @Scheduled(fixedDelay = 60000, initialDelay = 20000)
    public void updateNewestBalance() {
        logger.log(Level.INFO, () -> "update NewestBalance called");
        long usagePerMinute = 190000;
        long latestSolar = energyBalanceStoreRepo.findFirstByType(Type.SOLAR).getProduction();
        long latestNuclear = 6300;
        long latestWind = energyBalanceStoreRepo.findFirstByType(Type.WIND).getProduction();
        long total = +latestNuclear + latestWind + latestSolar;
        long leverage = 156150;
        total += leverage;

        double balance = ((float) total / usagePerMinute) * 100;
        //per minute4

        var energyBalance = new EnergyBalance(UUID.randomUUID(), usagePerMinute, total, balance, LocalDateTime.now(ZoneOffset.UTC));

        //Need to be checked, because something went wrong
        var message = new EnergyBalanceDTO(energyBalance.getConsume(), energyBalance.getProduction(), energyBalance.getBalance(), energyBalance.getTime());
        rabbitTemplate.convertAndSend("WebSockets", "websockets.balance.update", message.toString());
        logger.info("Sending message...."  + message);
        energyBalanceRepo.save(energyBalance);
    }
}

package org.energygrid.east.energybalanceservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.energygrid.east.energybalanceservice.model.BalanceType;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.model.EnergyBalanceDTO;
import org.energygrid.east.energybalanceservice.model.Type;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyUsageRepo;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Autowired
    private final EnergyBalanceRepo energyBalanceRepo;

    @Autowired
    private final EnergyUsageRepo energyUsageRepo;

    //  for testing purposes
    public EnergyService(EnergyBalanceRepo energyBalanceRepo, EnergyBalanceStoreRepo energyBalanceStoreRepo, EnergyUsageRepo energyUsageRepo, RabbitTemplate rabbitTemplate) {
        this.energyBalanceRepo = energyBalanceRepo;
        this.energyBalanceStoreRepo = energyBalanceStoreRepo;
        this.energyUsageRepo = energyUsageRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public EnergyBalance getLatestBalance() {
        logger.log(Level.INFO, () -> "latest balance called");

        return energyBalanceRepo.findFirstByOrderByTimeDesc();
    }


    @Override
    @Scheduled(fixedDelay = 60000, initialDelay = 40000)

    public void updateNewestBalance() {

        try {
            long latestSolar = 0;
            long latestWind = 0;
            logger.log(Level.INFO, () -> "update NewestBalance called");
            var lastEnergyUsageMinute = energyUsageRepo.findFirstByOrderByDayDesc().getKwh();
            var usagePerMinute = (lastEnergyUsageMinute * 1844100);
            if (energyBalanceStoreRepo.existsByType(Type.SOLAR)) {
                latestSolar = energyBalanceStoreRepo.findFirstByType(Type.SOLAR).getProduction();
            }
            if (energyBalanceStoreRepo.existsByType(Type.WIND)) {
                latestWind = energyBalanceStoreRepo.findFirstByType(Type.WIND).getProduction();
            }
            long latestNuclear = 6300;

            //  usage per minute and total has extra kwh
            //  now its everything from the region not only houses. Now the

            usagePerMinute += 170000;
            long total = +latestNuclear + latestSolar + latestWind;
            total += 160000;

            double balance = ((float) total / usagePerMinute) * 100;
            //per minute
            var energyBalance = new EnergyBalance();

            if (balance <= 99) {
                double balanceShortage = 100 - balance;
                double kwhNeeded = (usagePerMinute / 100) * balanceShortage;
                rabbitTemplate.convertAndSend("energymarket", "energymarket.balance.buy", kwhNeeded);
                energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.SHORTAGE, LocalDateTime.now(ZoneOffset.UTC));
                energyBalanceRepo.save(energyBalance);
            }

            if (balance > 100) {
                double balanceSurplus = balance - 100;
                double kwhSell = (usagePerMinute / 100) * balanceSurplus;
                rabbitTemplate.convertAndSend("energymarket", "energymarket.balance.sell", kwhSell);
                energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.SURPLUS, LocalDateTime.now(ZoneOffset.UTC));
                energyBalanceRepo.save(energyBalance);
            }

            if (balance >= 99 && balance <= 100) {
                energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.NORMAL, LocalDateTime.now(ZoneOffset.UTC));
                energyBalanceRepo.save(energyBalance);
            }

            var energyBalanceDTO = new EnergyBalanceDTO(energyBalance.getConsume(), energyBalance.getProduction(), energyBalance.getBalance(), energyBalance.getTime().toString());
            var message = objectMapper.writeValueAsString(energyBalanceDTO);
            rabbitTemplate.convertAndSend("websockets", "websocket.balance.update", message);
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }

    }
}

package org.energygrid.east.energybalanceservice.service;

import org.energygrid.east.energybalanceservice.model.BalanceType;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Autowired
    private EnergyBalanceRepo energyBalanceRepo;

    @Autowired
    private EnergyUsageRepo energyUsageRepo;

    @Override
    public EnergyBalance getLatestBalance() {
        logger.log(Level.INFO, () -> "latest balance called");

        return energyBalanceRepo.findFirstByOrderByTimeDesc();

    }

    @Scheduled(fixedDelay = 60000, initialDelay = 20000)
    public void updateNewestBalance() {
        logger.log(Level.INFO, () -> "update NewestBalance called");
        var lastEnergyUsageMinute = energyUsageRepo.findFirstByOrderByDayDesc().getKwh();

        var usagePerMinute = (lastEnergyUsageMinute * 1000000);
//       long latestSolar = energyBalanceStoreRepo.findFirstByType(Type.SOLAR).getProduction();
        long latestWind = energyBalanceStoreRepo.findFirstByType(Type.WIND).getProduction();
        long latestNuclear = 6300;

        long total = +latestNuclear + 8000 + latestWind;

        long leverage = 25000;
        total += leverage;

        double balance = ((float) total / usagePerMinute) * 100;
        //per minute

        if (balance <= 99) {
            double kwhNeeded = 100 - balance;
            rabbitTemplate.convertAndSend("energymarket", "energymarket.balance.buy", kwhNeeded);
            var energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.SHORTAGE, LocalDateTime.now(ZoneOffset.UTC));
            energyBalanceRepo.save(energyBalance);
        }

        if (balance > 100) {
            double kwhSell = balance - 100;
            rabbitTemplate.convertAndSend("energymarket", "energymarket.balance.sell", kwhSell);
            var energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.SURPLUS, LocalDateTime.now(ZoneOffset.UTC));
            energyBalanceRepo.save(energyBalance);
        }

        if (balance >= 99 && balance <= 100) {
            var energyBalance = new EnergyBalance(UUID.randomUUID(), (long) usagePerMinute, total, balance, BalanceType.NORMAL, LocalDateTime.now(ZoneOffset.UTC));
            energyBalanceRepo.save(energyBalance);
        }

    }
}

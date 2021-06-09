package org.energygrid.east.energybalanceservice;


import org.energygrid.east.energybalanceservice.model.BalanceType;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.model.EnergyUsage;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.IRabbitService;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class balanceTests {

    @Mock
    private EnergyBalanceRepo energyBalanceRepo;

    @Autowired
    private IEnergyService energyService;

    @Autowired
    private IRabbitService rabbitService;

    @Test
    void addLatestUsage() {
        rabbitService.addLatestWind(6000);
        rabbitService.addLatestSolar(6000);
        rabbitService.addLatestNuclear("6000");
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 2000, .022, 11).toString();
        rabbitService.addLatestUsage(energyUsage);
        energyService.updateNewestBalance();
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 100, 100, BalanceType.NORMAL, LocalDateTime.now()));
        EnergyBalance energyBalance = energyBalanceRepo.findFirstByOrderByTimeDesc();
        Assertions.assertNotNull(energyBalance);
    }

    @Test
    void addLatestSurplus() {
        rabbitService.addLatestWind(6000);
        rabbitService.addLatestSolar(6000);
        rabbitService.addLatestNuclear("6000");
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 2000, .022, 11).toString();
        rabbitService.addLatestUsage(energyUsage);
        energyService.updateNewestBalance();
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 120, 120, BalanceType.SURPLUS, LocalDateTime.now()));
        EnergyBalance energyBalance = energyBalanceRepo.findFirstByOrderByTimeDesc();
        Assertions.assertNotNull(energyBalance);
    }

    @Test
    void addLatestShortage() {
        rabbitService.addLatestWind(6000);
        rabbitService.addLatestSolar(6000);
        rabbitService.addLatestNuclear("6000");
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 2000, .022, 11).toString();
        rabbitService.addLatestUsage(energyUsage);
        energyService.updateNewestBalance();
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 99, 100, 99, BalanceType.SHORTAGE, LocalDateTime.now()));
        EnergyBalance energyBalance = energyBalanceRepo.findFirstByOrderByTimeDesc();
        Assertions.assertNotNull(energyBalance);
    }
}

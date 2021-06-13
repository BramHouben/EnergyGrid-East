package org.energygrid.east.energybalanceservice;


import org.energygrid.east.energybalanceservice.model.*;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.IRabbitService;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.RabbitService;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyUsageRepo;
import org.energygrid.east.energybalanceservice.service.EnergyService;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class balanceTests {

    // 0.004333333333333333 per min kwh
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private EnergyBalanceRepo energyBalanceRepo;

    @Mock
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Mock
    private EnergyUsageRepo energyUsageRepo;

    @Autowired
    private IEnergyService energyService;

    @Autowired
    private IRabbitService rabbitService;

    @BeforeEach
    void before() {
        energyService = new EnergyService(energyBalanceRepo, energyBalanceStoreRepo, energyUsageRepo, rabbitTemplate);
        rabbitService = new RabbitService(energyBalanceStoreRepo, energyUsageRepo);
        energyBalanceStoreRepo.deleteAll();
        energyBalanceRepo.deleteAll();
        energyUsageRepo.deleteAll();
    }

    @AfterEach
    void clean() {
        energyBalanceStoreRepo.deleteAll();
        energyBalanceRepo.deleteAll();
        energyUsageRepo.deleteAll();
    }

    @Test
    void addLatestUsage() {

        energyBalanceRepo.deleteAll();
        rabbitService.addLatestWind(6000);
        rabbitService.addLatestSolar(60000);
        rabbitService.addLatestNuclear("6000");
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 0.004333333333333333, 0.22, 1).toString();
        rabbitService.addLatestUsage(energyUsage);
        var energyUsage2 = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 0.004333333333333333, 0.22, 1);
//        rabbitService.addLatestUsage(energyUsage);
        Mockito.when(energyUsageRepo.findFirstByOrderByDayDesc()).thenReturn(energyUsage2);
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 120, 120, BalanceType.SURPLUS, LocalDateTime.now()));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.SOLAR)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 6000, Type.SOLAR));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.WIND)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 6000, Type.WIND));
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 100, 100, BalanceType.NORMAL, LocalDateTime.now()));
        EnergyBalance energyBalance = energyBalanceRepo.findFirstByOrderByTimeDesc();
        Assertions.assertDoesNotThrow(() -> energyService.updateNewestBalance());

        Assertions.assertEquals(BalanceType.NORMAL, energyBalance.getBalanceType());
//        Assertions.assertDoesNotThrow(()-> energyService.updateNewestBalance());
    }

    @Test
    void addLatestSurplus() {
        energyBalanceRepo.deleteAll();
        rabbitService.addLatestWind(600000);
        rabbitService.addLatestSolar(600000);
        rabbitService.addLatestNuclear("600000");
        var energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 0.004333333333333333, 0.22, 1);
//        rabbitService.addLatestUsage(energyUsage);
        Mockito.when(energyUsageRepo.findFirstByOrderByDayDesc()).thenReturn(energyUsage);
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 120, 120, BalanceType.SURPLUS, LocalDateTime.now()));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.SOLAR)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 6000, Type.SOLAR));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.WIND)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 6000, Type.WIND));

        Assertions.assertDoesNotThrow(() -> energyService.updateNewestBalance());

        EnergyBalance energyBalance = energyService.getLatestBalance();
        Assertions.assertEquals(BalanceType.SURPLUS, energyBalance.getBalanceType());
    }

    @Test
    void addLatestShortage() {
        var energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 0.004333333333333333, 0.22, 1);
        Mockito.when(energyUsageRepo.findFirstByOrderByDayDesc()).thenReturn(energyUsage);
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 99, 99, BalanceType.SHORTAGE, LocalDateTime.now()));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.SOLAR)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 1000, Type.SOLAR));
        Mockito.when(energyBalanceStoreRepo.findFirstByType(Type.WIND)).thenReturn(new EnergyBalanceStore(UUID.randomUUID(), LocalDateTime.now(), 1000, Type.WIND));
        Assertions.assertDoesNotThrow(() -> energyService.updateNewestBalance());

        EnergyBalance energyBalance = energyService.getLatestBalance();

        Assertions.assertEquals(BalanceType.SHORTAGE, energyBalance.getBalanceType());
    }

    @Test
    void getLatestBalance() {
        rabbitService.addLatestWind(6000);
        rabbitService.addLatestSolar(6000);
        rabbitService.addLatestNuclear("6000");
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 0.004333333333333333, .022, 1).toString();
        rabbitService.addLatestUsage(energyUsage);
        Mockito.when(energyBalanceRepo.findFirstByOrderByTimeDesc()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 100, 100, BalanceType.NORMAL, LocalDateTime.now()));
        Assertions.assertDoesNotThrow(() -> energyService.updateNewestBalance());

        EnergyBalance energyBalance = energyService.getLatestBalance();
        Assertions.assertNotNull(energyBalance);
    }
}

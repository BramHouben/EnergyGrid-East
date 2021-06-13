package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.model.EnergyUsage;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.IRabbitService;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.RabbitService;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyUsageRepo;
import org.energygrid.east.energybalanceservice.service.EnergyService;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RabbitServiceTest {


    @Mock
    private EnergyBalanceRepo energyBalanceRepo;

    @Mock
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Mock
    private EnergyUsageRepo energyUsageRepo;

    @Autowired
    private IRabbitService rabbitService;

    @BeforeEach
    void before() {
        rabbitService = new RabbitService(energyBalanceStoreRepo, energyUsageRepo);
        energyBalanceStoreRepo.deleteAll();
        energyBalanceRepo.deleteAll();
        energyUsageRepo.deleteAll();
    }

    @Test
    void addLatestWind() {


        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestWind(100));
    }

    @Test
    void addLatestSolar() {


        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestSolar(100));
    }

    @Test
    void addLatestNuclear() {


        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestNuclear("100"));
    }

    @Test
    void addLatestUsage() {
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(), "1", "1", 2000, .022, 11).toString();
        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestUsage(energyUsage));
    }

}
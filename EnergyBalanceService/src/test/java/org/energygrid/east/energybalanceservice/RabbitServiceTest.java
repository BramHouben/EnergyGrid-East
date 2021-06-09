package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.model.EnergyBalanceStore;
import org.energygrid.east.energybalanceservice.model.EnergyUsage;
import org.energygrid.east.energybalanceservice.model.Type;
import org.energygrid.east.energybalanceservice.rabbit.rabbitservice.IRabbitService;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RabbitServiceTest {

//    @Mock
//    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Autowired
    private IRabbitService rabbitService;

    @Test
    void addLatestWind() {

//        var energyBalanceStore = new EnergyBalanceStore();
//        energyBalanceStore.setUuid(UUID.randomUUID());
//        energyBalanceStore.setType(Type.WIND);
//        energyBalanceStore.setProduction(100);
//        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
//        given(energyBalanceStoreRepo.save(energyBalanceStore)).willReturn(energyBalanceStore);

        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestWind(100));
    }

    @Test
    void addLatestSolar() {
//        var energyBalanceStore = new EnergyBalanceStore();
//        energyBalanceStore.setUuid(UUID.randomUUID());
//        energyBalanceStore.setType(Type.SOLAR);
//        energyBalanceStore.setProduction(100);
//        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
//        given(energyBalanceStoreRepo.save(energyBalanceStore)).willReturn(energyBalanceStore);

        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestSolar(100));
    }

    @Test
    void addLatestNuclear() {
//        var energyBalanceStore = new EnergyBalanceStore();
//        energyBalanceStore.setUuid(UUID.randomUUID());
//        energyBalanceStore.setType(Type.NUCLEAR);
//        energyBalanceStore.setProduction(100);
//        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
//        given(energyBalanceStoreRepo.save(energyBalanceStore)).willReturn(energyBalanceStore);

        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestNuclear("100"));
    }

    @Test
    void addLatestUsage() {
        String energyUsage = new EnergyUsage(UUID.randomUUID().toString(),"1","1",2000,.022,11).toString();
        Assertions.assertDoesNotThrow(() -> rabbitService.addLatestUsage(energyUsage));
    }

}
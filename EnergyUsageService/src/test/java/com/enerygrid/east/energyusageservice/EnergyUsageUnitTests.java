package com.enerygrid.east.energyusageservice;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import com.enerygrid.east.energyusageservice.repository.EnergyUsageRepository;
import com.enerygrid.east.energyusageservice.service.EnergyUsageService;
import com.enerygrid.east.energyusageservice.service.IEnergyUsageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EnergyUsageUnitTests {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private IEnergyUsageService energyUsageService;
    @MockBean
    private EnergyUsageRepository energyUsageRepository;

    @BeforeEach
    void test() {
        String date = "10-06-2021";
        EnergyUsage usage = new EnergyUsage(String.valueOf(0), "1", date, 2.1, 1.1, 0);
        energyUsageRepository.insert(usage);
        energyUsageService = new EnergyUsageService(energyUsageRepository, rabbitTemplate);
    }

    @Test
    void getEnergyUsageOfUserWithExistingDataTest() {
        List<EnergyUsage> energyUsageList = new ArrayList<>();
        String date = "10-06-2021";

        for (var i = 1; i <= 24; i++) {
            EnergyUsage usage = new EnergyUsage(String.valueOf(i), "1", date, 2.1, 1.1, i);
            energyUsageList.add(usage);
        }

        Mockito.when(energyUsageRepository.findUsageByUserIdAndDay("1", "10-06-2021")).thenReturn(energyUsageList);

        var result = energyUsageService.getEnergyUsageOfUser("1", "10-06-2021");

        assertEquals(24, result.size());
    }

    @Test
    void getEnergyUsageOfUserWithoutExistingDataTest() {
        List<EnergyUsage> energyUsageList = new ArrayList<>();
        String date = "10-06-2021";

        for (var i = 1; i <= 24; i++) {
            EnergyUsage usage = new EnergyUsage(String.valueOf(i), "1", date, 2.1, 1.1, i);
            energyUsageList.add(usage);
        }

        Mockito.when(energyUsageRepository.findUsageByUserIdAndDay("1", "10-06-2021")).thenReturn(energyUsageList);

        var result = energyUsageService.getEnergyUsageOfUser("1", "11-06-2021");

        assertEquals(24, result.size());
    }

}

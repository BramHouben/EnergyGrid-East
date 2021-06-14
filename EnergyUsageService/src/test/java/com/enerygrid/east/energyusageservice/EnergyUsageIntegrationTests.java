package com.enerygrid.east.energyusageservice;

import com.enerygrid.east.energyusageservice.controller.EnergyUsageController;
import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import com.enerygrid.east.energyusageservice.service.IEnergyUsageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EnergyUsageIntegrationTests {

    @InjectMocks
    private EnergyUsageController energyUsageController;

    @Mock
    private IEnergyUsageService energyUsageService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(energyUsageController).build();
    }

    @Test
    public void returnOkGetLatestScenarioTest() throws Exception{
        List<EnergyUsage> energyUsageList = new ArrayList<>();
        String date = "10-06-2021";

        for (var i = 1; i <= 24; i++) {
            EnergyUsage usage = new EnergyUsage(String.valueOf(i), "1", date, 2.1, 1.1, i);
            energyUsageList.add(usage);
        }

        when(energyUsageService.getEnergyUsageOfUser("1", date)).thenReturn(energyUsageList);

        mockMvc.perform(get("/usage/day").contentType(MediaType.APPLICATION_JSON).param("date", date).characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBadRequestLatestScenarioTest() throws Exception{
        List<EnergyUsage> energyUsageList = new ArrayList<>();
        String date = "10-06-2021";

        for (var i = 1; i <= 24; i++) {
            EnergyUsage usage = new EnergyUsage(String.valueOf(i), "1", date, 2.1, 1.1, i);
            energyUsageList.add(usage);
        }

        when(energyUsageService.getEnergyUsageOfUser("1", date)).thenReturn(energyUsageList);

        mockMvc.perform(get("/usage/day").contentType(MediaType.APPLICATION_JSON).param("date", "10-07-2021").characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}

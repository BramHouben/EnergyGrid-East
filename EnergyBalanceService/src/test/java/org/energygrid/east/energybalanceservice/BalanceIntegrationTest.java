package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.controller.EnergyBalanceController;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BalanceIntegrationTest {


    @InjectMocks
    private EnergyBalanceController energyBalanceController;

    @Mock
    private IEnergyService energyService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(energyBalanceController).build();
    }

    @Test
    void returnOkGetLatestBalance() throws Exception {


        when(energyService.getLatestBalance()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 100, 100, balanceType, LocalDateTime.now()));

        mockMvc.perform(get("/energybalance/currentbalance"))
                .andExpect(status().isOk());
    }

}

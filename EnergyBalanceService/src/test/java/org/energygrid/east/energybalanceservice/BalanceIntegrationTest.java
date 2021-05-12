package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.controller.EnergyBalanceController;
import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceIntegrationTest {


    @InjectMocks
    private EnergyBalanceController energyBalanceController;

    @Mock
    private IEnergyService energyService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(energyBalanceController).build();
    }

    @Test
    public void returnOkGetLatestBalance() throws Exception {


        when(energyService.getLatestBalance()).thenReturn(new EnergyBalance(UUID.randomUUID(), 100, 100, 100, LocalDateTime.now()));

        mockMvc.perform(get("/energybalance/currentbalance"))
                .andExpect(status().isOk());
    }

}

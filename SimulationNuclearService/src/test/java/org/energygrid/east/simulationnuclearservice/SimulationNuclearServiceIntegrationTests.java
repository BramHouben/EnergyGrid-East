package org.energygrid.east.simulationnuclearservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.energygrid.east.simulationnuclearservice.controller.SimulationNuclearController;
import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.AddNuclearPowerplantDTO;
import org.energygrid.east.simulationnuclearservice.service.ISimulationNuclearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SimulationNuclearServiceIntegrationTests {

    @InjectMocks
    private SimulationNuclearController simulationNuclearController;

    @Mock
    private ISimulationNuclearService simulationNuclearService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(simulationNuclearController).build();
    }

//    @Test
//    void addSimulationTest() throws Exception {
//        var simulation = new AddNuclearPowerplantDTO("Test", 1, new Point(2,2), 1000, 1999);
//
//        when(simulationNuclearService.addSimulation(simulation)).thenReturn(new Simulation(UUID.randomUUID(), "Test", 1, new Point(2,2), 1000, 1999));
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(simulation);
//
//        mockMvc.perform(post("/simulation/nuclear/create").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")).andExpect(status().isOk());
//    }

    @Test
    void removeSimulationTest() throws Exception {
        var uuid = UUID.randomUUID();

        when(simulationNuclearService.removeSimulation(uuid)).thenReturn(true);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(uuid);

        mockMvc.perform(post("/simulation/nuclear/remove").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")).andExpect(status().isOk());
    }

    @Test
    void getAllSimulationsTest() throws Exception {
        List<Simulation> simulations = new ArrayList<>();

        simulations.add(new Simulation(UUID.randomUUID(), "Test", 1, new Point(2,2), 1000, 1999));
        simulations.add(new Simulation(UUID.randomUUID(), "Test2", 1, new Point(2,2), 1000, 1999));
        simulations.add(new Simulation(UUID.randomUUID(), "Test3", 1, new Point(2,2), 1000, 1999));

        when(simulationNuclearService.getSimulations()).thenReturn(simulations);

        mockMvc.perform(get("/simulation/nuclear/all")).andExpect(status().isOk());
    }
}

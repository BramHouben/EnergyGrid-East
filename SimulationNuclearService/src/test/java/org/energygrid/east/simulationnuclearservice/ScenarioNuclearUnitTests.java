package org.energygrid.east.simulationnuclearservice;

import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.model.enums.ScenarioType;
import org.energygrid.east.simulationnuclearservice.repository.ScenarioNuclearRepository;
import org.energygrid.east.simulationnuclearservice.repository.SimulationNuclearRepository;
import org.energygrid.east.simulationnuclearservice.service.IScenarioNuclearService;
import org.energygrid.east.simulationnuclearservice.service.ScenarioNuclearService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ScenarioNuclearUnitTests {

    @Autowired
    private IScenarioNuclearService scenarioNuclearService;

    @MockBean
    private SimulationNuclearRepository simulationNuclearRepository;

    @MockBean
    private ScenarioNuclearRepository scenarioNuclearRepository;

    @BeforeEach
    public void Start(){
        scenarioNuclearService = new ScenarioNuclearService(simulationNuclearRepository,scenarioNuclearRepository);
    }

    @Test
    void addReactorTest() {
        var uuid = UUID.randomUUID();

        ScenarioDTO scenarioDTO = new ScenarioDTO(uuid, "Test", LocalDateTime.now(), LocalDateTime.now(), 8, ScenarioType.ADD_REACTOR, 1000);

        List<Simulation> simulations = new ArrayList<>();
        simulations.add(new Simulation(UUID.randomUUID(), "Test2", 3, new Point(2, 2), 1000, 1999));

        Mockito.when(simulationNuclearRepository.findAll()).thenReturn(simulations);

        var result = scenarioNuclearService.createScenario(scenarioDTO);

        Assertions.assertEquals("Test", result.getName());
    }

    @Test
    void shutOffReactorTest() {
        var uuid = UUID.randomUUID();

        ScenarioDTO scenarioDTO = new ScenarioDTO(uuid, "Test", LocalDateTime.now(), LocalDateTime.now(), 8, ScenarioType.SHUTOFF_REACTOR, 1000);

        var simulation = new Simulation(uuid, "Test", 3, new Point(2, 2), 1000, 1999);

        Mockito.when(simulationNuclearRepository.getSimulationBySimulationId(uuid)).thenReturn(simulation);

        var result = scenarioNuclearService.createScenario(scenarioDTO);

        Assertions.assertEquals("Test", result.getName());

    }

    @Test
    void removeReactorTest() {
        var uuid = UUID.randomUUID();

        ScenarioDTO scenarioDTO = new ScenarioDTO(uuid, "Test", LocalDateTime.now(), LocalDateTime.now(), 8, ScenarioType.REMOVE_REACTOR, 1000);

        var simulation = new Simulation(uuid, "Test", 3, new Point(2, 2), 1000, 1999);

        List<Simulation> simulations = new ArrayList<>();
        simulations.add(simulation);
        simulations.add(new Simulation(UUID.randomUUID(), "Test2", 3, new Point(2, 2), 1000, 1999));

        Mockito.when(simulationNuclearRepository.findAll()).thenReturn(simulations);
        Mockito.when(simulationNuclearRepository.getSimulationBySimulationId(uuid)).thenReturn(simulation);

        var result = scenarioNuclearService.createScenario(scenarioDTO);

        Assertions.assertEquals("Test", result.getName());
    }
}

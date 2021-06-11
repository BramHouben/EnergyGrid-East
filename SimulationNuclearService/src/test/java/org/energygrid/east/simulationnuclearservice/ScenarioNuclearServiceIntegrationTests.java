package org.energygrid.east.simulationnuclearservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.energygrid.east.simulationnuclearservice.controller.ScenarioNuclearController;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.model.enums.ScenarioType;
import org.energygrid.east.simulationnuclearservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationnuclearservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationnuclearservice.service.IScenarioNuclearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ScenarioNuclearServiceIntegrationTests {

    @InjectMocks
    private ScenarioNuclearController scenarioNuclearController;

    @Mock
    private IScenarioNuclearService scenarioNuclearService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(scenarioNuclearController).build();
    }

//    @Test
//    void createScenarioTest() throws Exception {
//        var time = LocalDateTime.now();
//        var id = UUID.randomUUID();
//
//        ScenarioDTO scenarioDTO = new ScenarioDTO(id, "Test", time, time, 8, ScenarioType.ADD_REACTOR, 1000);
//        var scenarioExpectationResult = new ScenarioExpectationResult(id.toString(), "Test", ScenarioType.ADD_REACTOR, time.toString(), new SimulationExpectationResult(), "", new Point(2,2));
//
//        when(scenarioNuclearService.createScenario(scenarioDTO)).thenReturn(scenarioExpectationResult);
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(scenarioDTO);
//
//        mockMvc.perform(post("/scenario/nuclear/create").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8")).andExpect(status().isOk());
//    }

    @Test
    void getScenariosTest() throws Exception {
        List<ScenarioExpectationResult> scenarios = new ArrayList<>();

        scenarios.add(new ScenarioExpectationResult(UUID.randomUUID().toString(), "Test", ScenarioType.ADD_REACTOR, LocalDateTime.now().toString(), new SimulationExpectationResult(), "", new Point(2,2)));
        scenarios.add(new ScenarioExpectationResult(UUID.randomUUID().toString(), "Test2", ScenarioType.ADD_REACTOR, LocalDateTime.now().toString(), new SimulationExpectationResult(), "", new Point(2,2)));

        when(scenarioNuclearService.getScenarios()).thenReturn(scenarios);

        mockMvc.perform(get("/scenario/nuclear/all")).andExpect(status().isOk());
    }

    @Test
    void getLatestScenariosTest() throws Exception {
        List<ScenarioExpectationResult> scenarios = new ArrayList<>();

        scenarios.add(new ScenarioExpectationResult(UUID.randomUUID().toString(), "Test", ScenarioType.ADD_REACTOR, LocalDateTime.now().toString(), new SimulationExpectationResult(), "", new Point(2,2)));
        scenarios.add(new ScenarioExpectationResult(UUID.randomUUID().toString(), "Test2", ScenarioType.ADD_REACTOR, LocalDateTime.now().toString(), new SimulationExpectationResult(), "", new Point(2,2)));
        scenarios.add(new ScenarioExpectationResult(UUID.randomUUID().toString(), "Test2", ScenarioType.ADD_REACTOR, LocalDateTime.now().toString(), new SimulationExpectationResult(), "", new Point(2,2)));

        when(scenarioNuclearService.getLatestScenarios()).thenReturn(scenarios);

        mockMvc.perform(get("/scenario/nuclear/latest")).andExpect(status().isOk());
    }
}

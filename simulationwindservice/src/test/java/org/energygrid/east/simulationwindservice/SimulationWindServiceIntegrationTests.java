package org.energygrid.east.simulationwindservice;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationwindservice.controller.ScenarioWindController;
import org.energygrid.east.simulationwindservice.factory.FactoryURL;
import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.repository.ScenarioWindRepository;
import org.energygrid.east.simulationwindservice.service.IScenarioWindService;
import org.energygrid.east.simulationwindservice.service.ScenarioWindService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.geo.Point;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.energygrid.east.simulationwindservice.model.enums.EScenarioType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SimulationWindServiceIntegrationTests {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationWindServiceIntegrationTests.class);

    @InjectMocks
    private ScenarioWindController scenarioWindController;

    @Autowired
    private IScenarioWindService scenarioWindService;

    @MockBean
    ScenarioWindRepository scenarioWindRepository;

    @Autowired
    ApplicationContext context;

    @Mock
    private RestTemplate restTemplate;

    private MockMvc mockMvc;

    @BeforeAll
    static void setupAll() {
        LOG.info("Logic Tests");
    }

    @BeforeEach
    void setup() {
        LOG.info("[Begin test]");
        scenarioWindService = new ScenarioWindService(scenarioWindRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(scenarioWindController).build();
    }

    @AfterEach
    void tearDown() {
        LOG.info("[End test]");
    }

    @AfterAll
    static void tearDownAll() {
        LOG.info("-- End --");
    }

    @Test
    void testGetNullScenariosOfToday() {
        when(scenarioWindRepository.count()).thenReturn(0L);

        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertEquals(0L, scenarioCount);
        verify(scenarioWindRepository).count();
        LOG.info("Number of scenarios about wind- parks/ turbines: {} ", scenarioCount);
    }

    @Test
    void testGetALotOfScenariosOfToday() {
        when(scenarioWindRepository.count()).thenReturn(20L);

        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertEquals(20L, scenarioCount);
        verify(scenarioWindRepository).count();
        LOG.info("Number of scenarios about wind- parks/ turbines: {} ", scenarioCount);
    }

    @Test
    void testFailedGetScenariosOfToday() {
        when(scenarioWindRepository.count()).thenReturn(20L);

        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertNotEquals(0L, scenarioCount);
        verify(scenarioWindRepository).count();
        LOG.info("Number of scenarios about wind- parks/ turbines expected: {} | unexpected: {} ", scenarioCount, 0);
    }

    @Test
    void testAddScenario() {
        //var result = new ScenarioExpectationResult("Production", EScenarioType.ADD_WIND_PARK, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), new SimulationExpectationResult(), "Test Add windpark", new Point(51.965177468519435, 5.854872754972738)
        var resultToReturnFromRepository = new ScenarioExpectationResult();
        when(scenarioWindRepository.save(any(ScenarioExpectationResult.class))).thenReturn(resultToReturnFromRepository);
        LOG.info("Scenario Saved!");

        when(scenarioWindRepository.count()).thenReturn(1L);
        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertEquals(1L, scenarioCount);
        verify(scenarioWindRepository).count();
        LOG.info("Number of scenarios: {}", scenarioCount);
    }

    @Test
    void testAddScenarioAddWindPark() {
        var dateString = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        //var result = new ScenarioExpectationResult("Production", EScenarioType.ADD_WIND_PARK, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), new SimulationExpectationResult(), "Test Add windpark", new Point(51.965177468519435, 5.854872754972738)
        var resultToReturnFromRepository = new ScenarioExpectationResult("Production", ADD_WIND_PARK, dateString, new SimulationExpectationResult("1", dateString, new ArrayList<>(), 120.00), "Test Add windpark", new Point(51.965177468519435, 5.854872754972738));
        when(scenarioWindRepository.save(any(ScenarioExpectationResult.class))).thenReturn(resultToReturnFromRepository);
        LOG.info("Scenario Saved!");

        when(scenarioWindRepository.count()).thenReturn(1L);
        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertEquals(1L, scenarioCount);
        LOG.info("Number of scenarios: {}", scenarioCount);

        LOG.info("Scenario Details: ");
        assertEquals("Production", resultToReturnFromRepository.getName());
        LOG.info("Name: " + resultToReturnFromRepository.getName());

        assertEquals(ADD_WIND_PARK, resultToReturnFromRepository.getScenarioType());
        LOG.info("Scenario Type: " + resultToReturnFromRepository.getScenarioType());

        assertEquals(dateString, resultToReturnFromRepository.getCreatedAt());
        assertEquals(resultToReturnFromRepository.getCreatedAt(), resultToReturnFromRepository.getSimulationExpectationResult().getCreatedAt());
        LOG.info("Date: " + dateString);

        assertEquals("Test Add windpark", resultToReturnFromRepository.getDescription());
        LOG.info("Description: " + resultToReturnFromRepository.getName());

        assertEquals(120.00, resultToReturnFromRepository.getSimulationExpectationResult().getKwTotalResult());
        LOG.info("Kilowatt: " + resultToReturnFromRepository.getSimulationExpectationResult().getKwTotalResult());

        when(scenarioWindRepository.count()).thenReturn(1L);
        verify(scenarioWindRepository).count();
    }

    @Test
    void testAddScenarioAddWindTurbine() {
        var dateString = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        var resultToReturnFromRepository = new ScenarioExpectationResult("Production", ADD_WIND_TURBINE, dateString, new SimulationExpectationResult("1", dateString, new ArrayList<>(), 120.00), "Test Add windpark", new Point(51.965177468519435, 5.854872754972738));
        when(scenarioWindRepository.save(any(ScenarioExpectationResult.class))).thenReturn(resultToReturnFromRepository);
        LOG.info("Scenario Saved!");
        List<ScenarioExpectationResult> scenarios = mock(List.class);
        scenarios.add(resultToReturnFromRepository);
        verify(scenarios).add(resultToReturnFromRepository);
    }

    @Test
    void testAddScenarioRemoveTurbineSuccess() {
        var windTurbine = new WindTurbine(7, "Windturbine 7", new Point(52.39138, 5.36222), 1.8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("name", "Remove turbine 7 Flevoland");
        map.add("scenarioType", "REMOVE_WIND_TURBINE");
        map.add("description", "Old turbine with low energy generation");
        map.add("windTurbine", windTurbine);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8140/scenario/wind/create", HttpMethod.POST, httpEntity, String.class);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    void testCalculateProductionInKilowattSuccess() throws Exception {
        var scenario = new Scenario();
        var dateString = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        var scenarioExpectationResult = new ScenarioExpectationResult("Production", ADD_WIND_TURBINE, dateString, new SimulationExpectationResult("1", dateString, new ArrayList<>(), 120.00), "Test Add windpark", new Point(51.965177468519435, 5.854872754972738));

        scenario.setName("Remove turbine 7 Flevoland");
        scenario.setScenarioType(REMOVE_WIND_TURBINE);
        scenario.setDescription("Old turbine with low energy generation");
        scenario.setWindTurbine(new WindTurbine(7, "Windturbine 7", new Point(52.39138, 5.36222), 1.8));

//        var weather = getWeather().get(0);
//        var dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());
//        var productionExpectation = new ProductionExpectation((0.75 * 3.0) * 1000, dateTime);

        when(scenarioWindService.createScenario(scenario)).thenReturn(scenarioExpectationResult);
        //Mockito.lenient().when(simulationLogic.createSimulationForWindTurbine(3.0, weather)).thenReturn(new ProductionExpectation((0.75 * 3.0) * 1000, dateTime));
        //Mockito.when(scenarioWindRepository.findAll().get(Mockito.anyInt())).thenReturn(new ScenarioExpectationResult());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(scenario);

        mockMvc.perform(post("/scenario/wind/create").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    private JsonArray getWeather() {
        return new FactoryURL().getWeatherData(new HttpHeaders(), new RestTemplate(), "https://api.openweathermap.org/data/2.5/onecall?lat=52.39138&lon=5.36222&exclude=current,minutely,daily,alerts&appid=00db843b9b6a113888e4743d04823bd3");
    }
}

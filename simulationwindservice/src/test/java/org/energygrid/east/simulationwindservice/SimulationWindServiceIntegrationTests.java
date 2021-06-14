package org.energygrid.east.simulationwindservice;

import org.energygrid.east.simulationwindservice.model.Point;
import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.repository.ScenarioWindRepository;
import org.energygrid.east.simulationwindservice.service.ScenarioWindService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.energygrid.east.simulationwindservice.model.enums.EScenarioType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class SimulationWindServiceIntegrationTests {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationWindServiceIntegrationTests.class);

    @InjectMocks
    private ScenarioWindService scenarioWindService;

    @MockBean
    ScenarioWindRepository scenarioWindRepository;

    @Autowired
    ApplicationContext context;

    @BeforeAll
    static void setupAll() {
        LOG.info("Logic Tests");
    }

    @BeforeEach
    void setup() {
        LOG.info("[Begin test]");
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
        when(scenarioWindService.getLatestScenarios()).thenReturn(new ArrayList<>());

        ScenarioWindRepository scenarioWindFromContext = context.getBean(ScenarioWindRepository.class);
        long scenarioCount = scenarioWindFromContext.count();

        assertNotEquals(0L, scenarioCount);
        verify(scenarioWindRepository).count();
        LOG.info("Number of scenarios about wind- parks/ turbines expected: {} | unexpected: {} ", scenarioCount, 0);
    }

    @Test
    void testLatestScenarios() {
        var dateString = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        List<ScenarioExpectationResult> scenarios = new ArrayList<>();
        scenarios.add(new ScenarioExpectationResult("Test 1", ADD_WIND_PARK, dateString,
                new SimulationExpectationResult(), "Test 1", new Point(51.965177468519435, 5.854872754972738)));
        scenarios.add(new ScenarioExpectationResult("Test 2", REMOVE_WIND_TURBINE, dateString,
                new SimulationExpectationResult(), "Test 2", new Point(51.965177468519435, 5.854872754972738)));

        when(scenarioWindService.getLatestScenarios()).thenReturn(scenarios);
        when(scenarioWindRepository.saveAll(scenarios)).thenReturn(scenarios);

        ScenarioWindRepository scenarioWindFormContext = context.getBean(ScenarioWindRepository.class);
        var expectedScenarios = scenarioWindFormContext.findTop3ByOrderByCreatedAtDesc();
        assertEquals(2, expectedScenarios.size());
        verify(scenarioWindRepository).findTop3ByOrderByCreatedAtDesc();
    }

    @Test
    void testAddScenario() {
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
        var scenario = new Scenario();
        var currentDate = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        var scenarioExpectationResult = new ScenarioExpectationResult("Production", REMOVE_WIND_TURBINE, currentDate, new SimulationExpectationResult("1", currentDate, new ArrayList<>(), 120.00), "Test Add windpark", new Point(52.39138, 5.36222));

        scenario.setName("Remove turbine 7 Flevoland");
        scenario.setScenarioType(REMOVE_WIND_TURBINE);
        scenario.setDescription("Maintenance");
        scenario.setWindTurbine(windTurbine);
        scenario.setCoordinates(new Point(52.39138, 5.36222));

        when(scenarioWindRepository.save(scenarioExpectationResult)).thenReturn(scenarioExpectationResult);
        when(scenarioWindService.createScenario(scenario)).thenReturn(scenarioExpectationResult);
        assertNotNull(scenario);
    }

    @Test
    void testTurnOffTemporarilyTurbine() {
        var windTurbine = new WindTurbine(7, "Windturbine 7", new Point(52.39138, 5.36222), 1.8);
        var currentDate = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        var scenarioExpectationResult = new ScenarioExpectationResult("Production", TURN_OFF_WIND_TURBINE, currentDate, new SimulationExpectationResult("1", currentDate, new ArrayList<>(), 120.00), "Test Add windpark", new Point(52.39138, 5.36222));
        var localDate = LocalDate.now();
        String dateString = localDate.getYear() + "-" + localDate.getMonthValue() + 1 + "-" + localDate.getDayOfMonth() + "T";
        var scenario = new Scenario();
        scenario.setName("Remove turbine 7 Flevoland");
        scenario.setScenarioType(TURN_OFF_WIND_TURBINE);
        scenario.setDescription("Maintenance");
        scenario.setWindTurbine(windTurbine);
        scenario.setCoordinates(new Point(52.39138, 5.36222));
        scenario.setWindTurbineOffTimes(dateString + "18:00, " + dateString + "19:00, " + dateString + "20:00");

        when(scenarioWindRepository.save(scenarioExpectationResult)).thenReturn(scenarioExpectationResult);
        when(scenarioWindService.createScenario(scenario)).thenReturn(scenarioExpectationResult);
        assertNotNull(scenario);
    }

    @Test
    void testCalculateProductionRemoveTurbine() {
        var scenario = new Scenario();
        scenario.setName("Remove turbine 7 Flevoland");
        scenario.setScenarioType(REMOVE_WIND_TURBINE);
        scenario.setDescription("Old turbine with low energy generation");
        scenario.setWindTurbine(new WindTurbine(7, "Windturbine 7", new Point(52.39138, 5.36222), 1.8));
        scenario.setCoordinates(new Point(52.39138, 5.36222));

        var result = scenarioWindService.createScenario(scenario);
        assertNotNull(result);
        assertNotNull(result.getSimulationExpectationResult().getKwTotalResult());
        verify(scenarioWindRepository).save(any(ScenarioExpectationResult.class));
    }

    @Test
    void testCalculateProductionAddTurbine()  {
        var scenario = new Scenario();
        scenario.setName("New turbine for Flevoland");
        scenario.setScenarioType(ADD_WIND_TURBINE);
        scenario.setDescription("New 3.0 turbine in Flevoland");
        scenario.setWindTurbine(new WindTurbine(111, "Windturbine 111", new Point(52.39138, 5.36222), 3.0));
        scenario.setCoordinates(new Point(52.39138, 5.36222));

        var result = scenarioWindService.createScenario(scenario);
        assertNotNull(result);
        assertNotNull(result.getSimulationExpectationResult().getKwTotalResult());
        verify(scenarioWindRepository).save(any(ScenarioExpectationResult.class));
    }

    @Test
    void testCalculateProductionAddPark()  {
        var scenario = new Scenario();
        scenario.setName("5 New turbines for Flevoland");
        scenario.setScenarioType(ADD_WIND_PARK);
        scenario.setDescription("New 5x 3.0 turbines in Flevoland");
        scenario.setType(3.0);
        scenario.setCoordinates(new Point(52.39138, 5.36222));
        scenario.setAmount("5");

        var result = scenarioWindService.createScenario(scenario);
        assertNotNull(result);
        verify(scenarioWindRepository).save(any(ScenarioExpectationResult.class));
    }

    @Test
    void testCalculateProductionTurnOffWindPark()  {
        var windTurbine = new WindTurbine(7, "Windturbine 7", new Point(52.39138, 5.36222), 3.0);
        var localDate = LocalDate.now();
        var dateString = localDate.getYear() + "-" + localDate.getMonthValue() + 1 + "-" + localDate.getDayOfMonth() + "T";
        var scenario = new Scenario();
        scenario.setName("5 New turbines for Flevoland");
        scenario.setScenarioType(ADD_WIND_PARK);
        scenario.setDescription("New 5x 3.0 turbines in Flevoland");
        scenario.setCoordinates(new Point(52.39138, 5.36222));
        scenario.setWindTurbine(windTurbine);
        scenario.setWindTurbineOffTimes(dateString + "18:00, " + dateString + "19:00, " + dateString + "20:00");

        var result = scenarioWindService.createScenario(scenario);
        assertNotNull(result);
        verify(scenarioWindRepository).save(any(ScenarioExpectationResult.class));
    }

    @Test
    void testCountScenariosTodayNull() {
        var result = scenarioWindService.countScenariosToday();
        assertNull(result);
    }

    @Test
    void testCountScenariosToday() {
        var dateString = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        List<ScenarioExpectationResult> scenarios = new ArrayList<>();

        for (var i = 1; i <= 3; i++) {
            scenarios.add(new ScenarioExpectationResult("Test " + i, ADD_WIND_PARK, dateString,
                    new SimulationExpectationResult(String.valueOf(i), dateString, new ArrayList<>(), 1200.0 * i), "Test " + i, new Point(51.965177468519435, 5.854872754972738)));
        }

        var date = LocalDate.now();
        String startDate = date + "T00:00:00Z";
        String endDate = date + "T23:59:59Z";

        when(scenarioWindRepository.findAllByCreatedAtBetween(startDate, endDate)).thenReturn(scenarios);

        var result = scenarioWindService.countScenariosToday();
        LOG.info("Result: KWH : {}", result.getKilowatt());
        assertNotNull(result);
        assertThat(result.getKilowatt(), greaterThan(0.0));
    }
}

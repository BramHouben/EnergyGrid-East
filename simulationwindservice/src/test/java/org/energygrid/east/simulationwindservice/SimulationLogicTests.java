package org.energygrid.east.simulationwindservice;

import org.energygrid.east.simulationwindservice.factory.FactoryURL;
import org.energygrid.east.simulationwindservice.logic.ISimulationLogic;
import org.energygrid.east.simulationwindservice.logic.SimulationLogic;
import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class SimulationLogicTests {

    private static final Logger LOG = LoggerFactory.getLogger(SimulationLogicTests.class);

    private ISimulationLogic simulationLogic;
    private RestTemplate template;
    private HttpHeaders headers;

    @BeforeAll
    static void setupAll() {
        LOG.info("Logic Tests");
    }

    @BeforeEach
    void setup() {
        LOG.info("[Begin test]");
        this.simulationLogic = new SimulationLogic();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
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
    void testFactorForWindSpeedGreaterThan3LowerThan10() {
        var expected = 0.8;
        assertEquals(expected, calculateFactor(8).getFactorValue());
    }

    @Test
    void testFactorForWindSpeedGreaterThan10LowerThan25() {
        var expected = 1.0;
        assertEquals(expected, calculateFactor(20).getFactorValue());
    }

    @Test
    void testFactorForWindSpeedGreaterThan25() {
        var expected = 0.0;
        assertEquals(expected, calculateFactor(26).getFactorValue());
    }

    @Test
    void testFactorForWindSpeedLowerThan3() {
        var expected = 0.0;
        assertEquals(expected, calculateFactor(2).getFactorValue());
    }

    @Test
    void testGetProductionInKwhWindSpeed20TurbineType18() {
        var expected = 1800.0;
        assertEquals(expected, getProductionExpectationInKilowatt(1, 1.8).getKw());
    }

    @Test
    void testGetProductionInKwhWindSpeed20TurbineType20() {
        var expected = 2000.0;
        assertEquals(expected, getProductionExpectationInKilowatt(1, 2.0).getKw());
    }

    @Test
    void testGetProductionInKwhWindSpeed20TurbineType30() {
        var expected = 3000.0;
        assertEquals(expected, getProductionExpectationInKilowatt(1, 3.0).getKw());
    }

    @Test
    void testGetProductionInKwhWindSpeed6TurbineType30() {
        var expected = 1800.0;
        var actual = Math.round(getProductionExpectationInKilowatt(0.6, 3.0).getKw() * 100) / 100;
        assertEquals(expected, actual);
    }

    @Test
    void testGetProductionInKwhWindSpeed2TurbineType30() {
        var expected = 0.0;
        assertEquals(expected, getProductionExpectationInKilowatt(0.0, 3.0).getKw());
    }

    @Test
    void testGetProductionInKwhWindSpeed28TurbineType30() {
        var expected = 0.0;
        assertEquals(expected, getProductionExpectationInKilowatt(0.0, 3.0).getKw());
    }

    @Test
    void testGetCalculationKwProduction() {
        List<SimulationResult> simulationResultList = new ArrayList<>();
        List<ProductionExpectation> productionExpectations = new ArrayList<>();
        for (var i = 0; i < 240; i += 10) {
            productionExpectations.add(new ProductionExpectation(i * 4.5, LocalDateTime.now()));
        }
        simulationResultList.add(new SimulationResult("Production", 1, productionExpectations));

        var kilowatt = simulationLogic.calculateKwProduction(simulationResultList, true);
        LOG.info("-- Kilowatt production = {}", kilowatt);
        assertNotNull(kilowatt);
        assertEquals(12420.0, kilowatt);
    }

    @Test
    void testGetCalculationKwMissedProduction() {
        List<SimulationResult> simulationResultList = new ArrayList<>();
        List<ProductionExpectation> productionExpectations = new ArrayList<>();
        for (var i = 0; i < 240; i += 10) {
            productionExpectations.add(new ProductionExpectation(i * 3.5, LocalDateTime.now()));
        }
        simulationResultList.add(new SimulationResult("Missed Production", 1, productionExpectations));

        var kilowatt = simulationLogic.calculateKwProduction(simulationResultList, false);
        LOG.info("-- Kilowatt missed production = {}", kilowatt);
        assertNotNull(kilowatt);
        assertEquals(-9660.0, kilowatt);
    }

    @Test
    void testCreateSimulationForWindTurbine30() {
        var data = new FactoryURL().getWeatherData(headers, template, "https://api.openweathermap.org/data/2.5/onecall?lat=52.39138&lon=5.36222&exclude=current,minutely,daily,alerts&appid=00db843b9b6a113888e4743d04823bd3");
        var weather = data.get(0);
        var productionExpectation = simulationLogic.createSimulationForWindTurbine(3.0, weather);

        assertNotNull(productionExpectation);
        LOG.info("-- Kilowatt production = {}", productionExpectation.getKw());
    }

    private Factor calculateFactor(double windSpeed) {
        return simulationLogic.calculateFactor("Test wind speed Factor", windSpeed);
    }

    private ProductionExpectation getProductionExpectationInKilowatt(double factor, double type) {
        return simulationLogic.getProductionExpectationInKw(factor, type, LocalDateTime.now());
    }
}

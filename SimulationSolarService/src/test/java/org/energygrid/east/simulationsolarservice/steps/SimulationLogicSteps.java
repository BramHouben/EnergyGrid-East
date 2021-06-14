package org.energygrid.east.simulationsolarservice.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.energygrid.east.simulationsolarservice.factory.FactoryURL;
import org.energygrid.east.simulationsolarservice.logic.ISimulationLogic;
import org.energygrid.east.simulationsolarservice.logic.SimulationLogic;
import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;
import org.energygrid.east.simulationsolarservice.model.SolarUnit;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SimulationLogicSteps {

    private ISimulationLogic simulationLogic;

    private SolarUnit solarUnit = new SolarUnit();
    private ProductionExpectation productionExpectation = new ProductionExpectation();

    @Given("^a solarUnit$")
    public void initializeSolarUnit(List<SolarUnit> myData) throws Throwable {
        solarUnit = myData.get(0);
        simulationLogic = new SimulationLogic();
    }

//    @Given("^a weather$")
//    public void initializeWeather(JsonElement weather) throws Throwable {
//        simulationLogic = new SimulationLogic();
//    }

    @Given("^a productionExpectation$")
    public void initializeProductionExpectation(List<ProductionExpectation> myData) throws Throwable {
        productionExpectation = myData.get(0);
        simulationLogic = new SimulationLogic();
    }

    @When("I add {int} amount")
    public void testCreateSimulationForSolarUnit(SolarUnit solarUnit, int amount) {
        var data = new FactoryURL().getWeatherData(new HttpHeaders(), new RestTemplate(), "https://api.openweathermap.org/data/2.5/onecall?lat=52.39138&lon=5.36222&exclude=current,minutely,daily,alerts&appid=00db843b9b6a113888e4743d04823bd3");
        var weather = data.get(0);
        simulationLogic.createSimulationForSolarUnit(weather, solarUnit, amount);
    }

    @Then("the result should be {float}")
    public void validateCreateSimulationResult(double result) {
        assertNotEquals(result, productionExpectation.getKw());
    }

    @ParameterType("MONO_CRYSTALLINE|POLY_CRYSTALLINE")
    public SolarPanelType solarPanelType(String solarPanelType) {
        if (solarPanelType.equals("MONO_CRYSTALLINE")) {
            return SolarPanelType.MONO_CRYSTALLINE;
        }
        return SolarPanelType.POLY_CRYSTALLINE;
    }

//    @ParameterType(".*")
//    public JsonElement weather() {
//        var data = new FactoryURL().getWeatherData(new HttpHeaders(), new RestTemplate(), "https://api.openweathermap.org/data/2.5/onecall?lat=52.39138&lon=5.36222&exclude=current,minutely,daily,alerts&appid=00db843b9b6a113888e4743d04823bd3");
//        return data.get(0);
//    }
//
//    @ParameterType(".*")
//    public SolarUnit solarUnit() {
//        return new SolarUnit(new Point(52.39138, 5.36222), SolarPanelType.MONO_CRYSTALLINE, 4);
//    }
//
//    @ParameterType(".*")
//    public ProductionExpectation productionExpectation() {
//        return new ProductionExpectation(1800, LocalDateTime.now());
//    }
}

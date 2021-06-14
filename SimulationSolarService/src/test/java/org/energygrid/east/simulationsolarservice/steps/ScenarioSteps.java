package org.energygrid.east.simulationsolarservice.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.energygrid.east.simulationsolarservice.model.Scenario;
import org.energygrid.east.simulationsolarservice.model.enums.ScenarioType;
import org.junit.Assert;

public class ScenarioSteps {

    private Scenario scenario;

    @Given("^I have a scenario$")
    public void initializeScenario() {
         scenario = new Scenario();
    }

    @When("^I add (-?\\d+)$")
    public void scenarioAddProperty(int amount) {
        scenario.setAmount(amount);
    }

    @When("I add {string} name and add {int} amount and add {scenarioType} scenarioType")
    public void scenarioAddProperties(String name, int amount, ScenarioType scenarioType) {
        scenario.setName(name);
        scenario.setAmount(amount);
        scenario.setScenarioType(scenarioType);
    }

    @Then("^the result should be (-?\\d+)$")
    public void validateResult(int result) {
        Assert.assertEquals(scenario.getAmount(), result);
    }

    @Then("the result should be {string} and {int} and {scenarioType}")
    public void validateScenarioResult(String name, int amount, ScenarioType scenarioType) {
        Assert.assertEquals(scenario.getName(), name);
        Assert.assertEquals(scenario.getAmount(), amount);
        Assert.assertEquals(scenario.getScenarioType(), scenarioType);
    }

    @ParameterType("ADD_SOLAR_PARK|REMOVE_SOLAR_PARK|TURN_OFF_SOLAR_PARK")
    public ScenarioType scenarioType(String scenarioType) {
        switch (scenarioType) {
            case "REMOVE_SOLAR_PARK":
                return ScenarioType.REMOVE_SOLAR_PARK;
            case "TURN_OFF_SOLAR_PARK":
                return ScenarioType.TURN_OFF_SOLAR_PARK;
            default:
                return ScenarioType.ADD_SOLAR_PARK;
        }
    }
}

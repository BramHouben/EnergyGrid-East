package org.energygrid.east.simulationsolarservice;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@CucumberContextConfiguration
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/scenario.feature")
public class ScenarioTests {
}

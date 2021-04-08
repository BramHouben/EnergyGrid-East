package org.energygrid.east.simulationwindservice.service;

import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;

public interface IScenarioWindService {
    ScenarioExpectationResult createScenario(Scenario scenario);
}

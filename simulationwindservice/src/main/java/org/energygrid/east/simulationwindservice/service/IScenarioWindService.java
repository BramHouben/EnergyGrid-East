package org.energygrid.east.simulationwindservice.service;

import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.ScenarioWindResponse;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;

import java.util.List;

public interface IScenarioWindService {
    ScenarioExpectationResult createScenario(Scenario scenario);
    List<ScenarioExpectationResult> getLatestScenarios();

    /**
     * Count all current scenario's of today
     */
    ScenarioWindResponse countScenariosToday();
}

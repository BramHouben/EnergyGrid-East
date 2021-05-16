package org.energygrid.east.simulationsolarservice.service;

import org.energygrid.east.simulationsolarservice.model.Scenario;
import org.energygrid.east.simulationsolarservice.model.results.ScenarioExpectationResult;

import java.util.List;

public interface IScenarioSolarScenario {

    /**
     *
     * @param scenario
     * @return the created scenario
     */
    ScenarioExpectationResult createScenario(Scenario scenario);

    /**
     * Find latest 3 scenarios
     * @return the latest 3 scenarios
     */
    List<ScenarioExpectationResult> getLatestScenarios();

    /**
     * Count all current scenario's of today
     */
    int countScenariosToday();
}

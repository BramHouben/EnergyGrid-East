package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.model.results.ScenarioExpectationResult;

import java.util.List;

public interface IScenarioNuclearService {
    ScenarioExpectationResult createScenario(ScenarioDTO scenarioDTO);

    List<ScenarioExpectationResult> getScenarios();

    List<ScenarioExpectationResult> getLatestScenarios();
}

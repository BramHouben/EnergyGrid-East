package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;

import java.util.List;

public interface IScenarioNuclearService {
    Scenario createScenario(ScenarioDTO scenarioDTO);
    List<Scenario> getScenarios();
}

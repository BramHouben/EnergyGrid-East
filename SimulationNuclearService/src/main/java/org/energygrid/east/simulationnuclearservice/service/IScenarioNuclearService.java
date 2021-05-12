package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;

public interface IScenarioNuclearService {
    Scenario createScenario(ScenarioDTO scenarioDTO);

}

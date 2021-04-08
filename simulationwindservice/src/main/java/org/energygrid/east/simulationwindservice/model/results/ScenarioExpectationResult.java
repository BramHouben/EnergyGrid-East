package org.energygrid.east.simulationwindservice.model.results;

import org.energygrid.east.simulationwindservice.model.enums.EScenarioType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scenario")
public class ScenarioExpectationResult {

    @Id
    private String scenarioId;

    private String name;
    private EScenarioType scenarioType;
    private SimulationExpectationResult simulationExpectationResult;

    public ScenarioExpectationResult() {}

    public ScenarioExpectationResult(String name, EScenarioType scenarioType, SimulationExpectationResult simulationExpectationResult) {
        this.name = name;
        this.scenarioType = scenarioType;
        this.simulationExpectationResult = simulationExpectationResult;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EScenarioType getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(EScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }

    public SimulationExpectationResult getSimulationExpectationResult() {
        return simulationExpectationResult;
    }

    public void setSimulationExpectationResult(SimulationExpectationResult simulationExpectationResult) {
        this.simulationExpectationResult = simulationExpectationResult;
    }
}

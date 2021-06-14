package org.energygrid.east.simulationwindservice.model.results;

import org.energygrid.east.simulationwindservice.model.Point;
import org.energygrid.east.simulationwindservice.model.enums.EScenarioType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "scenario")
public class ScenarioExpectationResult {

    @Id
    private String scenarioId;

    private String name;
    private EScenarioType scenarioType;

    @Field("created_at")
    private String createdAt;
    private SimulationExpectationResult simulationExpectationResult;

    private String description;
    private Point coordinates;

    public ScenarioExpectationResult() {
    }

    public ScenarioExpectationResult(String name, EScenarioType scenarioType, String createdAt, SimulationExpectationResult simulationExpectationResult, String description, Point coordinates) {
        this.name = name;
        this.scenarioType = scenarioType;
        this.createdAt = createdAt;
        this.simulationExpectationResult = simulationExpectationResult;
        this.description = description;
        this.coordinates = coordinates;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public SimulationExpectationResult getSimulationExpectationResult() {
        return simulationExpectationResult;
    }

    public void setSimulationExpectationResult(SimulationExpectationResult simulationExpectationResult) {
        this.simulationExpectationResult = simulationExpectationResult;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}

package org.energygrid.east.simulationnuclearservice.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "simulation")
public class SimulationExpectationResult {

    @Id
    private String simulationId;

    @Field("created_at")
    private String createdAt;
    private List<SimulationResult> simulationResults;
    private Double kwTotalResult;

    public SimulationExpectationResult() {
        this.simulationResults = new ArrayList<>();
    }

    public SimulationExpectationResult(String simulationId, String createdAt, List<SimulationResult> simulationResults, Double kwTotalResult) {
        this.simulationId = simulationId;
        this.createdAt = createdAt;
        this.simulationResults = simulationResults;
        this.kwTotalResult = kwTotalResult;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(String simulationId) {
        this.simulationId = simulationId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(List<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    public Double getKwTotalResult() {
        return kwTotalResult;
    }

    public void setKwTotalResult(Double kwTotalResult) {
        this.kwTotalResult = kwTotalResult;
    }
}

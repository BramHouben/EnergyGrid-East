package org.energygrid.east.simulationwindservice.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "simulation")
public class SimulationExpectationResult {

    @Id
    private String simulationId;
    private List<SimulationResult> simulationResults;

    public SimulationExpectationResult() {
        this.simulationResults = new ArrayList<>();
    }

    public SimulationExpectationResult(String simulationId, List<SimulationResult> simulationResults) {
        this.simulationId = simulationId;
        this.simulationResults = simulationResults;
    }

    public String getSimulationId() { return simulationId; }

    public void setSimulationId(String simulationId) { this.simulationId = simulationId; }

    public List<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(List<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }
}

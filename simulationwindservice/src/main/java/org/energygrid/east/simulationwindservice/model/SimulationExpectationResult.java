package org.energygrid.east.simulationwindservice.model;

import java.util.ArrayList;
import java.util.List;

public class SimulationExpectationResult {

    private List<SimulationResult> simulationResults;

    public SimulationExpectationResult() {
        this.simulationResults = new ArrayList<>();
    }

    public SimulationExpectationResult(List<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    public List<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(List<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    public void addSimulationResults(SimulationResult simulationResults) {
        this.simulationResults.add(simulationResults);
    }
}

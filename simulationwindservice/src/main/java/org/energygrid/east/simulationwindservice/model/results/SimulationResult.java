package org.energygrid.east.simulationwindservice.model.results;

import org.energygrid.east.simulationwindservice.model.ProductionExpectation;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {

    private int turbineId;
    private List<ProductionExpectation> productionExpectations;

    public SimulationResult() {
        this.productionExpectations = new ArrayList<>();
    }

    public SimulationResult(int turbineId, List<ProductionExpectation> productionExpectations) {
        this.turbineId = turbineId;
        this.productionExpectations = productionExpectations;
    }

    public int getTurbineId() {
        return turbineId;
    }

    public void setTurbineId(int turbineId) {
        this.turbineId = turbineId;
    }

    public List<ProductionExpectation> getProductionExpectations() {
        return productionExpectations;
    }

    public void setProductionExpectations(List<ProductionExpectation> productionExpectations) {
        this.productionExpectations = productionExpectations;
    }

    public void addProductionExpectation(ProductionExpectation productionExpectation) {
        this.productionExpectations.add(productionExpectation);
    }
}

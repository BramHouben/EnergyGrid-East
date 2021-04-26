package org.energygrid.east.simulationsolarservice.model.results;

import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;

import java.util.ArrayList;
import java.util.List;

public class SimulationResult {

    private String name;
    private int turbineId;
    private List<ProductionExpectation> productionExpectations;

    public SimulationResult() {
        this.productionExpectations = new ArrayList<>();
    }

    public SimulationResult(String name, int turbineId, List<ProductionExpectation> productionExpectations) {
        this.name = name;
        this.turbineId = turbineId;
        this.productionExpectations = productionExpectations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

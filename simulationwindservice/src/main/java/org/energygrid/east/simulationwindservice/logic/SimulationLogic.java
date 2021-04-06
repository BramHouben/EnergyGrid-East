package org.energygrid.east.simulationwindservice.logic;

import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.SimulationResult;

import java.time.LocalDateTime;

public class SimulationLogic implements ISimulationLogic {

    @Override
    public Factor calculateFactor(String name, double windSpeed) {
        return new Factor(name, getWindSpeedFactor(windSpeed));
    }

//    @Override
//    public SimulationResult calculateSimulationResult(double factor, double typeTurbine, LocalDateTime dateTime) {
//        SimulationResult simulationResult = new SimulationResult();
//        simulationResult.setProductionExpectations(getProductionExpectationInKw(factor, typeTurbine, dateTime));
//        return simulationResult;
//    }

    private double getWindSpeedFactor(double windSpeed) {
        double factor = 0;

        if (windSpeed >= 10 || windSpeed < 25) {
            factor = 1;
        }

        if (windSpeed >= 3 || windSpeed < 10) {
            factor = 1 * (windSpeed / 10);
        }

        return factor;
    }

    @Override
    public ProductionExpectation getProductionExpectationInKw(double factor, double typeTurbine, LocalDateTime dateTime) {
        var result = (factor * typeTurbine) * 1000;
        return new ProductionExpectation(result, dateTime);
    }
}

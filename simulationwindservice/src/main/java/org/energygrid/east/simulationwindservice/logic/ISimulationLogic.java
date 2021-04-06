package org.energygrid.east.simulationwindservice.logic;

import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;

import java.time.LocalDateTime;

public interface ISimulationLogic {
    Factor calculateFactor(String name, double value);
    //SimulationResult calculateSimulationResult(double factor, double typeTurbine,  LocalDateTime dateTime);
    ProductionExpectation getProductionExpectationInKw(double factor, double typeTurbine, LocalDateTime dateTime);
}

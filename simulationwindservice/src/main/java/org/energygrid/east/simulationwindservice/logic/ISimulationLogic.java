package org.energygrid.east.simulationwindservice.logic;

import com.google.gson.JsonElement;
import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;

import java.time.LocalDateTime;

public interface ISimulationLogic {
    Factor calculateFactor(String name, double value);
    ProductionExpectation getProductionExpectationInKw(double factor, double typeTurbine, LocalDateTime dateTime);
    ProductionExpectation createSimulationForWindTurbine(double type, JsonElement weather);
}

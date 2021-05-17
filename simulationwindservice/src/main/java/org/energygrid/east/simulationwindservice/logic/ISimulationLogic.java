package org.energygrid.east.simulationwindservice.logic;

import com.google.gson.JsonElement;
import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;

import java.time.LocalDateTime;
import java.util.List;

public interface ISimulationLogic {
    /**
     * @param name
     * @param value
     * @return
     */
    Factor calculateFactor(String name, double value);

    /**
     * @param factor
     * @param typeTurbine type of turbine
     * @param dateTime time where production is made
     * @return expectation of production
     */
    ProductionExpectation getProductionExpectationInKw(double factor, double typeTurbine, LocalDateTime dateTime);

    /**
     * @param type of simulation
     * @param weather
     * @return an expectation of wind turbine energy output
     */
    ProductionExpectation createSimulationForWindTurbine(double type, JsonElement weather);

    /**
     * @param simulationResults result of simulations
     * @param isAdded is the simulation added
     * @return the total kw production
     */
    Double calculateKwProduction(List<SimulationResult> simulationResults, Boolean isAdded);
}

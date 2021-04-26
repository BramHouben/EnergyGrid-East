package org.energygrid.east.simulationsolarservice.logic;

import com.google.gson.JsonElement;
import org.energygrid.east.simulationsolarservice.model.Factor;
import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;
import org.energygrid.east.simulationsolarservice.model.SolarUnit;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.energygrid.east.simulationsolarservice.model.results.SimulationResult;

import java.time.LocalDateTime;
import java.util.List;

public interface ISimulationLogic {
    /**
     * @param name
     * @param uvi
     * @param solarPanelType
     * @param year
     * @return factor
     */
    Factor calculateFactor(String name, double uvi, SolarPanelType solarPanelType, int year);

    /**
     * @param factor
     * @param solarPanelType type of solar panel
     * @param dateTime time where production is made
     * @return expectation of production
     */
    ProductionExpectation getProductionExpectationInKw(double factor, SolarPanelType solarPanelType, LocalDateTime dateTime);

    /**
     * @param weather
     * @return an expectation of solar panel energy output
     */
    ProductionExpectation createSimulationForSolarUnit(JsonElement weather, SolarUnit solarUnit);

    /**
     * @param simulationResults result of simulations
     * @param isAdded is the simulation added
     * @return the total kw production
     */
    Double calculateKwProduction(List<SimulationResult> simulationResults, Boolean isAdded);
}

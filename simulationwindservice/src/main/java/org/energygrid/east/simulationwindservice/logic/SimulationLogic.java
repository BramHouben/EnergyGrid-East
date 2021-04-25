package org.energygrid.east.simulationwindservice.logic;

import com.google.gson.JsonElement;
import org.energygrid.east.simulationwindservice.model.Factor;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

public class SimulationLogic implements ISimulationLogic {

    @Override
    public Factor calculateFactor(String name, double windSpeed) {
        return new Factor(name, getWindSpeedFactor(windSpeed));
    }

    @Override
    public ProductionExpectation getProductionExpectationInKw(double factor, double typeTurbine, LocalDateTime dateTime) {
        var result = (factor * typeTurbine) * 1000;
        return new ProductionExpectation(result, dateTime);
    }

    @Override
    public ProductionExpectation createSimulationForWindTurbine(double type, JsonElement weather) {
        double value = weather.getAsJsonObject().get("wind_speed").getAsDouble();
        var factor = calculateFactor("Wind Speed", value);
        var dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

        return getProductionExpectationInKw(factor.getFactor(), type, dateTime);
    }

    @Override
    public Double calculateKwProduction(List<SimulationResult> simulationResults, Boolean isAdded) {
        var kwTotal = 0.0;
        for (var result : simulationResults) {
            for (var production : result.getProductionExpectations()) {
                if (result.getName() != null && result.getName().equals("Missed Production")) {
                    kwTotal = kwTotal - production.getKw();
                }
                else { kwTotal = kwTotal + production.getKw(); }
            }
        }

        return kwTotal;
    }

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
}

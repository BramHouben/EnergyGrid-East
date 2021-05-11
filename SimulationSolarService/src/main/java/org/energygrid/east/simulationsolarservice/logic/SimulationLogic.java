package org.energygrid.east.simulationsolarservice.logic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationsolarservice.model.Factor;
import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;
import org.energygrid.east.simulationsolarservice.model.SolarUnit;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.energygrid.east.simulationsolarservice.model.results.SimulationResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class SimulationLogic implements ISimulationLogic {

    @Override
    public Factor calculateFactor(String name, double uvi, SolarPanelType solarPanelType, int year) {
        return new Factor();
    }

    @Override
    public ProductionExpectation getProductionExpectationInKw(double factor, SolarPanelType solarPanelType, LocalDateTime dateTime) {
        return null;
    }

    @Override
    public ProductionExpectation createSimulationForSolarUnit(JsonElement weather, SolarUnit solarUnit) {
        double factor = getSolarPanelFactor(weather, solarUnit);
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());
        return new ProductionExpectation(factor, dateTime);
    }

    @Override
    public Double calculateKwProduction(List<SimulationResult> simulationResults, int amount, Boolean isAdded) {
        var kwTotal = 0.0;
        for (var result : simulationResults) {
            for (var production : result.getProductionExpectations()) {
                if (result.getName() != null && result.getName().equals("Missed Production")) {
                    kwTotal = kwTotal - production.getKw();
                } else {
                    kwTotal = kwTotal + production.getKw();
                }
            }
        }

        return kwTotal * amount;
    }

    private double getSolarPanelFactor(JsonElement weather, SolarUnit solarUnit) {
        double factor;

        switch (solarUnit.getSolarPanelType()) {
            case MONO_CRYSTALLINE:
                factor = calculateFactor(weather, 0.4, solarUnit);
                break;
            case POLY_CRYSTALLINE:
                factor = calculateFactor(weather, 0.5, solarUnit);
                break;
            default:
                factor = 0;
                break;
        }
        return factor;
    }

    private double calculateFactor(JsonElement weather, double coefficient, SolarUnit solarUnit) {
        double result = 0;
        double temperature = weather.getAsJsonObject().get("temp").getAsDouble();
        var uvi = weather.getAsJsonObject().get("uvi").getAsDouble();
        var correction = 0.85;

        if (uvi == 0) {
            return result;
        }

        result = solarUnit.getNumberOfPanels() * (0.25 * correction);

        if (uvi <= 3.5) {
            result = result * (uvi / 10);
        }

        var temp = kelvinToCelsius(temperature);

        if (temp > 25) {
            result = result * (1 - ((temperature - 25) * coefficient) / 100);
        }

        return result;
        //return (result * correction) * solarUnit.getNumberOfPanels();
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}

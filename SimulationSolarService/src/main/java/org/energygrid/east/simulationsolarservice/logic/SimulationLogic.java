package org.energygrid.east.simulationsolarservice.logic;

import com.google.gson.JsonElement;
import org.energygrid.east.simulationsolarservice.model.Factor;
import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;
import org.energygrid.east.simulationsolarservice.model.SolarUnit;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.energygrid.east.simulationsolarservice.model.results.SimulationResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
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
    public Double calculateKwProduction(List<SimulationResult> simulationResults, Boolean isAdded) {
        return null;
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
        //per 1 graad boven de 25 graden, gaat er 0.4% van de opwekking ervan af.
        //MONO 270WP tot 330WP (16% tot 20%) warmte coeff 0.4
        //POLY 220WP tot 270WP (12% tot 16%) warmte coeff 0.5
        double result = 0;
        double temperature = weather.getAsJsonObject().get("temp").getAsDouble() + 30;
        var uvi = weather.getAsJsonObject().get("uvi").getAsDouble();
        var correction = 0.85;

        if (uvi == 0) {
            return result;
        }

        result = solarUnit.getNumberOfPanels() * 0.27;

        if (uvi <= 1) {
            result = result * uvi;
        }

        if (temperature > 25) {
            result = result * (1 - ((temperature - 25) * coefficient) / 100);
        }

        return result * correction;
    }
}

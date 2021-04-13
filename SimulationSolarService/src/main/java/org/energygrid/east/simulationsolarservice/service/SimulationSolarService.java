package org.energygrid.east.simulationsolarservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.Kwh;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;
import org.energygrid.east.simulationsolarservice.rabbit.RabbitConsumer;
import org.energygrid.east.simulationsolarservice.rabbit.consumer.WeatherConsumer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class SimulationSolarService implements ISimulationSolarService {

    private final List<SimulationSolar> simulationSolars;
    private JsonObject weatherData;

    public SimulationSolarService() {
        simulationSolars = new ArrayList<>();
    }

    @Override
    public SimulationSolar getSimulationById(String id) {
        return simulationSolars.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void addSimulation(SimulationSolar simulationSolar) {
        simulationSolars.add(simulationSolar);
    }

    @Override
    public void deleteSimulation(String id) {
        SimulationSolar simulationSolar = simulationSolars.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        simulationSolars.remove(simulationSolar);
    }

    @Override
    public EnergyRegionSolarParksOutput simulateEnergyGrid(List<EnergyRegionSolarParksInput> energyRegionSolarParksInput) {

        RabbitConsumer<String> rabbitConsumer = new RabbitConsumer<>();
        WeatherConsumer weatherConsumer = new WeatherConsumer();

        String weather = rabbitConsumer.consume(weatherConsumer);
        if(weather != null) {
            JsonObject newWeatherData = new Gson().fromJson(weather, JsonObject.class);
            weatherData = newWeatherData;
        }

        var hours = weatherData.getAsJsonObject().get("hourly").getAsJsonArray();
        double correctionFactor = 0.85;
        double energyPerSolarPanel = 0.27;

        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = new EnergyRegionSolarParksOutput();

        for (var hour: hours) {
            double kwh = 0;
            LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(hour.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

            double uvi = hour.getAsJsonObject().get("uvi").getAsDouble();
            if(uvi == 0) {
                energyRegionSolarParksOutput.addKwh(new Kwh(0.0, triggerTime));
                continue;
            }

            for (var energypark : energyRegionSolarParksInput) {
                var baseKwh = energypark.getTotalCountSolarPanels() * energyPerSolarPanel;
                var finalKwh = baseKwh;
                finalKwh = baseKwh * correctionFactor;
                var temperature = hour.getAsJsonObject().get("temp").getAsDouble() + 30;

                if (temperature > 25) {
                    var temperatureCorrection = (temperature - 25) * 0.4;
                    finalKwh = finalKwh * (1 - (temperatureCorrection / 100));
                };
                kwh += finalKwh;
            }
            energyRegionSolarParksOutput.addKwh(new Kwh(kwh, triggerTime));
        }

        return energyRegionSolarParksOutput;
    }
}

package org.energygrid.east.simulationsolarservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.Kwh;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class SimulationSolarService implements ISimulationSolarService {

    private final List<SimulationSolar> simulationSolars;
    private final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=51.441642&lon=5.4697225&units=metric&exlude=current,minutely,daily,alerts&appid=d43994b92b8caae6ee650e65194f0ad8";
    private final RestTemplate template;
    private final HttpHeaders headers;

    public SimulationSolarService() {
        simulationSolars = new ArrayList<>();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
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

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        var weatherData =  new Gson().fromJson(response.getBody(), JsonObject.class);

        var hours = weatherData.getAsJsonObject().get("hourly").getAsJsonArray();
        double correctionFactor = 0.85;
        double energyPerSolarPanel = 0.27;

        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = new EnergyRegionSolarParksOutput();

        for (var hour: hours) {
            double kwh = 0;
            LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(hour.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

            if(hour.getAsJsonObject().get("uvi").getAsDouble() == 0) {
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

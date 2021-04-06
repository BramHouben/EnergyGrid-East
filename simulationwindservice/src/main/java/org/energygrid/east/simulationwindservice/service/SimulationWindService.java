package org.energygrid.east.simulationwindservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationwindservice.logic.ISimulationLogic;
import org.energygrid.east.simulationwindservice.logic.SimulationLogic;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.SimulationResult;
import org.energygrid.east.simulationwindservice.model.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

@Service
public class SimulationWindService implements ISimulationWindService {

    private ISimulationLogic simulationLogic;
    private final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=52.23587&lon=6.19775&exclude=current,minutely,daily,alerts&appid=d43994b92b8caae6ee650e65194f0ad8";
    private final RestTemplate template;
    private final HttpHeaders headers;

    @Autowired
    public SimulationWindService() {
        this.simulationLogic = new SimulationLogic();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Override
    public SimulationExpectationResult createSimulation() {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        var weatherData = new Gson().fromJson(response.getBody(), JsonObject.class);

        var data = weatherData.getAsJsonObject().get("hourly").getAsJsonArray();

        //TODO
        //Get All WindTurbines
        List<WindTurbine> turbines = new ArrayList<>();
        turbines.add(new WindTurbine(1, "WindTurbine 1", new Point(52.23587, 6.19775), 3.0));
        turbines.add(new WindTurbine(2, "WindTurbine 2", new Point(52.57085, 6.45386), 2.0));

        List results = new ArrayList<>();

        for (var turbine : turbines) {
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setTurbineId(turbine.getTurbineId());

            for (var weather : data) {
                simulationResult.addProductionExpectation(createSimulationForWindTurbine(turbine.getType(), weather));
            }
            results.add(simulationResult);
        }

        simulationExpectationResult.setSimulationResults(results);
        return simulationExpectationResult;
    }

    private ProductionExpectation createSimulationForWindTurbine(double type, JsonElement weather) {
        double value = weather.getAsJsonObject().get("wind_speed").getAsDouble();
        var factor = simulationLogic.calculateFactor("Wind Speed", value);
        var dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

        return simulationLogic.getProductionExpectationInKw(factor.getFactor(), type, dateTime);
    }
}

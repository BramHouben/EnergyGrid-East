package org.energygrid.east.simulationwindservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationwindservice.logic.ISimulationLogic;
import org.energygrid.east.simulationwindservice.logic.SimulationLogic;
import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;
import org.energygrid.east.simulationwindservice.repository.ScenarioWindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScenarioWindService implements IScenarioWindService {

    private ISimulationLogic simulationLogic;
    private final RestTemplate template;
    private final HttpHeaders headers;

    @Autowired
    private ScenarioWindRepository scenarioWindRepository;

    @Autowired
    public ScenarioWindService() {
        this.simulationLogic = new SimulationLogic();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Override
    public ScenarioExpectationResult createScenario(Scenario scenario) {
        ScenarioExpectationResult scenarioExpectationResult = new ScenarioExpectationResult();
        scenarioExpectationResult.setName(scenario.getName());
        scenarioExpectationResult.setScenarioType(scenario.getScenarioType());
        scenarioExpectationResult.setCreatedAt(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

        switch (scenario.getScenarioType()) {
            case ADD_WIND_PARK:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioAddWindPark(scenario.getAmount(), scenario.getCoordinates(), scenario.getType(), scenarioExpectationResult.getCreatedAt()));
                break;
            case ADD_WIND_TURBINE:
            case REMOVE_WIND_TURBINE:
                scenarioExpectationResult.setSimulationExpectationResult(createSimulationWindTurbine(scenario.getWindTurbine(),scenarioExpectationResult.getCreatedAt()));
                break;
            default:
                return null;
        }
        scenarioWindRepository.save(scenarioExpectationResult);
        return scenarioExpectationResult;
    }

    private SimulationExpectationResult createScenarioAddWindPark(String amount, Point coordinates, Double type, String createdAt) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);

        if (amount != null && coordinates != null && type != null) {
            List results = new ArrayList<>();

            for (var i = 1; i < Integer.parseInt(amount) + 1; i++) {
                SimulationResult simulationResult = new SimulationResult();
                simulationResult.setTurbineId(i);
                //Call weather based on coordinates
                var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+coordinates.getX()+"&lon="+coordinates.getY()+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
                var weatherData = new Gson().fromJson(response.getBody(), JsonObject.class);
                var data = weatherData.getAsJsonObject().get("hourly").getAsJsonArray();

                for (var weather : data) {
                    simulationResult.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(type, weather));
                }
                results.add(simulationResult);
            }
            simulationExpectationResult.setSimulationResults(results);
        }
        return simulationExpectationResult;
    }

    private SimulationExpectationResult createSimulationWindTurbine(WindTurbine windTurbine, String createdAt) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);
        List results = new ArrayList<>();

        if (windTurbine != null) {
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setTurbineId(windTurbine.getTurbineId());
            //Call weather based on coordinates
            var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+windTurbine.getCoordinates().getX()+"&lon="+windTurbine.getCoordinates().getY()+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            var weatherData = new Gson().fromJson(response.getBody(), JsonObject.class);
            var data = weatherData.getAsJsonObject().get("hourly").getAsJsonArray();

            for (var weather : data) {
                simulationResult.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(windTurbine.getType(), weather));
            }
            results.add(simulationResult);
        }
        simulationExpectationResult.setSimulationResults(results);

        return simulationExpectationResult;
    }
}

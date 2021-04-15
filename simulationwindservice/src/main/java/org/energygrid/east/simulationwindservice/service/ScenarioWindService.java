package org.energygrid.east.simulationwindservice.service;

import org.energygrid.east.simulationwindservice.factory.FactoryURL;
import org.energygrid.east.simulationwindservice.logic.ISimulationLogic;
import org.energygrid.east.simulationwindservice.logic.SimulationLogic;
import org.energygrid.east.simulationwindservice.model.ProductionExpectation;
import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;
import org.energygrid.east.simulationwindservice.repository.ScenarioWindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
        scenarioExpectationResult.setDescription(scenario.getDescription());
        scenarioExpectationResult.setCoordinates(scenario.getCoordinates() != null ? scenario.getCoordinates() : scenario.getWindTurbine().getCoordinates());

        switch (scenario.getScenarioType()) {
            case ADD_WIND_PARK:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioAddWindPark(scenario.getAmount(), scenario.getCoordinates(), scenario.getType(), scenarioExpectationResult.getCreatedAt()));
                break;
            case ADD_WIND_TURBINE:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioWindTurbine(scenario.getWindTurbine(), scenarioExpectationResult.getCreatedAt(), true));
                break;
            case REMOVE_WIND_TURBINE:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioWindTurbine(scenario.getWindTurbine(), scenarioExpectationResult.getCreatedAt(), false));
                break;
            case TURN_OFF_WIND_TURBINE:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioTurnOffWindTurbine(scenario.getWindTurbine(), scenario.getWindTurbineOffTimes(), scenarioExpectationResult.getCreatedAt()));
                break;
            default:
                return null;
        }
        scenarioWindRepository.save(scenarioExpectationResult);
        return scenarioExpectationResult;
    }

    @Override
    public List<ScenarioExpectationResult> getLatestScenarios() {
        return scenarioWindRepository.findTop3ByOrderByCreatedAtDesc();
    }

    private SimulationExpectationResult createScenarioAddWindPark(String amount, Point coordinates, Double type, String createdAt) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);

        if (amount != null && coordinates != null && type != null) {
            List<SimulationResult> results = new ArrayList<>();

            for (var i = 1; i < Integer.parseInt(amount) + 1; i++) {
                SimulationResult simulationResult = new SimulationResult();
                simulationResult.setTurbineId(i);
                var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+coordinates.getX()+"&lon="+coordinates.getY()+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
                var data = new FactoryURL().getWeatherData(headers, template, url);

                for (var weather : data) {
                    simulationResult.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(type, weather));
                }
                results.add(simulationResult);
            }
            simulationExpectationResult.setSimulationResults(results);
            simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, true));
        }
        return simulationExpectationResult;
    }

    private SimulationExpectationResult createScenarioWindTurbine(WindTurbine windTurbine, String createdAt, Boolean isAdded) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);
        List<SimulationResult> results = new ArrayList<>();

        if (windTurbine != null) {
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setName(isAdded ? "Production" : "Missed Production");
            simulationResult.setTurbineId(windTurbine.getTurbineId());
            //Call weather based on coordinates
            var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+windTurbine.getCoordinates().getX()+"&lon="+windTurbine.getCoordinates().getY()+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
            var data = new FactoryURL().getWeatherData(headers, template, url);

            for (var weather : data) {
                simulationResult.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(windTurbine.getType(), weather));
            }
            results.add(simulationResult);
        }
        simulationExpectationResult.setSimulationResults(results);
        simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, isAdded));

        return simulationExpectationResult;
    }

    private SimulationExpectationResult createScenarioTurnOffWindTurbine(WindTurbine windTurbine, List<String> dates, String createdAt) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);
        List<SimulationResult> results = new ArrayList<>();

        if (windTurbine != null) {
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setName("Production");
            simulationResult.setTurbineId(windTurbine.getTurbineId());

            SimulationResult simulationResultMissed = new SimulationResult();
            simulationResultMissed.setName("Missed Production");
            simulationResultMissed.setTurbineId(windTurbine.getTurbineId());
            //Call weather based on coordinates
            var url = "https://api.openweathermap.org/data/2.5/onecall?lat="+windTurbine.getCoordinates().getX()+"&lon="+windTurbine.getCoordinates().getY()+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
            var data = new FactoryURL().getWeatherData(headers, template, url);

            for (var weather : data) {
                var dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

                if (dates.stream().noneMatch(dateTime.toString()::equals)) {
                    simulationResult.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(windTurbine.getType(), weather));
                    simulationResultMissed.addProductionExpectation(new ProductionExpectation(0, dateTime));
                }
                else {
                    simulationResult.addProductionExpectation(new ProductionExpectation(0, dateTime));
                    simulationResultMissed.addProductionExpectation(simulationLogic.createSimulationForWindTurbine(windTurbine.getType(), weather));
                }
            }
            results.add(simulationResult);
            results.add(simulationResultMissed);
        }
        simulationExpectationResult.setSimulationResults(results);
        simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, true));

        return simulationExpectationResult;
    }
}

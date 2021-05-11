package org.energygrid.east.simulationsolarservice.service;

import org.energygrid.east.simulationsolarservice.factory.FactoryURL;
import org.energygrid.east.simulationsolarservice.logic.ISimulationLogic;
import org.energygrid.east.simulationsolarservice.logic.SimulationLogic;
import org.energygrid.east.simulationsolarservice.model.ProductionExpectation;
import org.energygrid.east.simulationsolarservice.model.Scenario;
import org.energygrid.east.simulationsolarservice.model.SolarPark;
import org.energygrid.east.simulationsolarservice.model.SolarUnit;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.energygrid.east.simulationsolarservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationsolarservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationsolarservice.model.results.SimulationResult;
import org.energygrid.east.simulationsolarservice.repository.ScenarioSolarRepository;
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
public class ScenarioSolarService implements IScenarioSolarScenario {

    private ISimulationLogic simulationLogic;
    private final RestTemplate template;
    private final HttpHeaders headers;

    @Autowired
    private ScenarioSolarRepository scenarioSolarRepository;

    @Autowired
    public ScenarioSolarService() {
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
        scenarioExpectationResult.setDescription(scenario.getDescription() != null ? scenario.getDescription() : scenario.getSolarUnit().getDescription());
        scenarioExpectationResult.setCoordinates(scenario.getCoordinates() != null ? scenario.getCoordinates() : scenario.getSolarUnit().getCoordinates());

        switch (scenario.getScenarioType()) {
            case ADD_SOLAR_PARK:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioSolarPark(scenario.getAmount(), scenarioExpectationResult.getCoordinates(), scenario.getSolarUnit(), scenarioExpectationResult.getCreatedAt(), true));
                break;
            case REMOVE_SOLAR_PARK:
                scenarioExpectationResult.setSimulationExpectationResult(createScenarioSolarPark(scenario.getAmount(), scenarioExpectationResult.getCoordinates(), scenario.getSolarUnit(), scenarioExpectationResult.getCreatedAt(), false));
                break;
            case TURN_OFF_SOLAR_PARK:
                scenarioExpectationResult.setSimulationExpectationResult(scenarioTurnOffSolarPark(scenario.getAmount(), scenario.getSolarUnit(), scenario.getTurnOffTimes(), scenarioExpectationResult.getCreatedAt()));
            case TURN_OFF_UNIT:
                break;
            default:
                return null;
        }
        scenarioSolarRepository.save(scenarioExpectationResult);
        return scenarioExpectationResult;
    }

    @Override
    public List<ScenarioExpectationResult> getLatestScenarios() {
        return scenarioSolarRepository.findTop3ByOrderByCreatedAtDesc();
    }

    private SimulationExpectationResult createScenarioSolarPark(int amount, Point coordinates, SolarUnit solarUnit, String createdAt, Boolean isAdded) {
        var simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);

        if (coordinates != null && solarUnit != null && amount >= 0 && solarUnit.getNumberOfPanels() > 0) {
            List<SimulationResult> results = new ArrayList<>();

            //Amount is to big, so we do at the end by the calculation 1 unit * amount
            for (var i = 0; i < 1; i++) {
                var simulationResult = new SimulationResult();
                simulationResult.setName(isAdded ? "Production" : "Missed Production");
                var data = new FactoryURL().getWeatherData(headers, template, getUrl(coordinates.getX(), coordinates.getY()));

                for (var weather : data) {
                    simulationResult.addProductionExpectation(simulationLogic.createSimulationForSolarUnit(weather, solarUnit));
                }
                results.add(simulationResult);
            }
            simulationExpectationResult.setSimulationResults(results);
            simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, amount, isAdded));
        }
        return simulationExpectationResult;
    }

    private SimulationExpectationResult scenarioTurnOffSolarPark(int amount, SolarUnit solarUnit, List<String> dates, String createdAt) {
        SimulationExpectationResult simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(createdAt);
        List<SimulationResult> results = new ArrayList<>();

        if (solarUnit != null) {
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setName("Production");

            SimulationResult simulationResultMissed = new SimulationResult();
            simulationResultMissed.setName("Missed Production");

            var data = new FactoryURL().getWeatherData(headers, template, getUrl(solarUnit.getCoordinates().getX(), solarUnit.getCoordinates().getY()));

            for (var weather : data) {
                var dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weather.getAsJsonObject().get("dt").getAsInt()), TimeZone.getDefault().toZoneId());

                for (var i = 0; i < amount; i++) {
                    if (dates.stream().noneMatch(dateTime.toString()::equals)) {
                        simulationResult.addProductionExpectation(simulationLogic.createSimulationForSolarUnit(weather, solarUnit));
                        simulationResultMissed.addProductionExpectation(new ProductionExpectation(0, dateTime));
                    }
                    else {
                        simulationResultMissed.addProductionExpectation(simulationLogic.createSimulationForSolarUnit(weather, solarUnit));
                        simulationResult.addProductionExpectation(new ProductionExpectation(0, dateTime));
                    }
                }
            }
            results.add(simulationResult);
            results.add(simulationResultMissed);
        }
        simulationExpectationResult.setSimulationResults(results);
        simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, 1, true));

        return simulationExpectationResult;
    }

    private String getUrl(double x, double y) {
        return "https://api.openweathermap.org/data/2.5/onecall?lat="+x+"&lon="+y+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
    }
}

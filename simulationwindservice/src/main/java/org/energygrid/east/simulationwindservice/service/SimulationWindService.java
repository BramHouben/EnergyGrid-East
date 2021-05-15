package org.energygrid.east.simulationwindservice.service;

import org.energygrid.east.simulationwindservice.factory.FactoryURL;
import org.energygrid.east.simulationwindservice.logic.ISimulationLogic;
import org.energygrid.east.simulationwindservice.logic.SimulationLogic;
import org.energygrid.east.simulationwindservice.model.WindTurbine;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationResult;
import org.energygrid.east.simulationwindservice.repository.SimulationWindRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SimulationWindService implements ISimulationWindService {

    private static final java.util.logging.Logger logger = Logger.getLogger(SimulationWindService.class.getName());
    private static final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=52.23587&lon=6.19775&exclude=current,minutely,daily,alerts&appid=d43994b92b8caae6ee650e65194f0ad8";
    private final RestTemplate template;
    private final HttpHeaders headers;
    private final ISimulationLogic simulationLogic;

    @Autowired
    private RabbitTemplate rabbittemplate;

    @Autowired
    private SimulationWindRepository simulationWindRepository;

    @Autowired
    public SimulationWindService() {
        this.simulationLogic = new SimulationLogic();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Scheduled(fixedDelay = 10000)
    private void sendLatestWindInfoToQueue() {
        logger.log(Level.INFO, "Send wind message to queue");
        //total kwh per minute
        var wind = "15010";
        rabbittemplate.convertAndSend("EnergyBalance", "balance.create.wind", wind);
    }

    @Override
    public SimulationExpectationResult createSimulation() {
        var simulationExpectationResult = new SimulationExpectationResult();
        simulationExpectationResult.setCreatedAt(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        List<SimulationResult> results = new ArrayList<>();

        var data = new FactoryURL().getWeatherData(headers, template, url);
        List<WindTurbine> turbines = new ArrayList<>();
        turbines.add(new WindTurbine(1, "WindTurbine 1", new Point(52.23587, 6.19775), 3.0));
        turbines.add(new WindTurbine(2, "WindTurbine 2", new Point(52.57085, 6.45386), 2.0));

        for (var turbine : turbines) {
            var simulationResult = new SimulationResult();
            simulationResult.setTurbineId(turbine.getTurbineId());

            for (var weather : data) {
                var productionExpectation = simulationLogic.createSimulationForWindTurbine(turbine.getType(), weather);
                simulationResult.addProductionExpectation(productionExpectation);
            }
            results.add(simulationResult);
        }
        simulationExpectationResult.setSimulationResults(results);
        simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, true));
        simulationWindRepository.save(simulationExpectationResult);
        return simulationExpectationResult;
    }
}

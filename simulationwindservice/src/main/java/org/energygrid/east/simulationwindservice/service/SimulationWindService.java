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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SimulationWindService implements ISimulationWindService {


    private static final java.util.logging.Logger logger = Logger.getLogger(SimulationWindService.class.getName());
    @Value("${KEYSECRET}")
    private static String url;
    private final RestTemplate template;
    private final HttpHeaders headers;
    private final ISimulationLogic simulationLogic;
    private final List<WindTurbine> windTurbines;
    @Autowired
    private RabbitTemplate rabbittemplate;

    @Autowired
    private SimulationWindRepository simulationWindRepository;

    @Autowired
    public SimulationWindService() {
        this.simulationLogic = new SimulationLogic();
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
        windTurbines = new ArrayList<>();
        var windTurbine1 = new WindTurbine(1, "test", new Point(6.46073, 52.57363), 3.0);
        var windTurbine2 = new WindTurbine(2, "test", new Point(6.46051, 52.57072), 3.0);
        var windTurbine3 = new WindTurbine(3, "test", new Point(6.45386, 52.57085), 3.0);
        var windTurbine4 = new WindTurbine(3, "test", new Point(6.45365, 52.56796), 3.0);
        var windTurbine5 = new WindTurbine(4, "test", new Point(6.46322, 52.57036), 3.0);
        windTurbines.add(windTurbine1);
        windTurbines.add(windTurbine2);
        windTurbines.add(windTurbine3);
        windTurbines.add(windTurbine4);
        windTurbines.add(windTurbine5);
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
    @PostConstruct
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    private void sendLatestWindInfo() {
        double total = 0;
        for (var turbine : windTurbines) {
            var data = new FactoryURL().getWeatherData(headers, template, getUrl(turbine.getCoordinates().getX(), turbine.getCoordinates().getY()));
            var productionExpectation = simulationLogic.createSimulationForWindTurbine(turbine.getType(), data.get(0));
            total = +productionExpectation.getKw();
        }
        //  this is temporary!
        var totalPerMinute = total * 3;
//       var totalPerMinute = total /60;
        rabbittemplate.convertAndSend("EnergyBalance", "balance.create.wind", totalPerMinute);
        logger.log(Level.INFO, () -> "Send wind message to queue: " + totalPerMinute);
    }

    private String getUrl(double x, double y) {
        return "https://api.openweathermap.org/data/2.5/onecall?lat=" + x + "&lon=" + y + "&exclude=current,minutely,daily,hourly,alerts&appid=49a9e8c085fe370f35bb3ff8bc855f40";
    }
}

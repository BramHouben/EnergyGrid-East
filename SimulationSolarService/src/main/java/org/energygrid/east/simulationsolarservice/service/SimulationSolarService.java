package org.energygrid.east.simulationsolarservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.energygrid.east.simulationsolarservice.factory.FactoryURL;
import org.energygrid.east.simulationsolarservice.logic.SimulationLogic;
import org.energygrid.east.simulationsolarservice.model.*;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.energygrid.east.simulationsolarservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationsolarservice.model.results.SimulationResult;
import org.energygrid.east.simulationsolarservice.repository.SolarParkProductionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SimulationSolarService implements ISimulationSolarService {

    private static final java.util.logging.Logger logger = Logger.getLogger(SimulationSolarService.class.getName());
    private final SimulationLogic simulationLogic;

    @Value("${URL}")
    private String url ;

    @Value("${APIKEY_SIMULATION}")
    private String apiKey;

    private final RestTemplate template;
    private final HttpHeaders headers;

    private static final List<SolarParkViewModel> solarParks = new ArrayList<>();

    static {
        solarParks.add(new SolarParkViewModel(1, "ENGIE Energie Zonnepark Harculo B.V.", new Point(52.46974613309643, 6.1132777251542105), 1350, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(2, "Sunwatt De Kwekerij B.V.", new Point(52.05817723871769, 6.30917082237407), 1000, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(3, "A - Avri Solar B.V.", new Point(51.86711327577287, 5.326008743927849), 400, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(4, "B - Avri Solar B.V.", new Point(51.86589798710941, 5.326571389399491), 400, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(5, "Obton Solenergi \"de Munt\" C.V.", new Point(52.722304383081756, 5.7842783364985655), 500, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(6, "Maximazon B.V.", new Point(52.57768011883653, 5.531332567397516), 500, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(7, "NPG Solar Dedemsvaart B.V. i.o.", new Point(52.59949708915047, 6.496428896477237), 1000, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(8, "Zonnepark Noordveen B.V.", new Point(52.15567779195109, 6.211231760397507), 1000, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(9, "Lingesolar B.V.", new Point(51.92255702793297, 5.471603806725014), 1400, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(10, "Endona Projecten B.V.", new Point(52.334961662742415, 6.288235045251693), 1400, SolarPanelType.POLY_CRYSTALLINE));

        solarParks.add(new SolarParkViewModel(11, "De Groene Weuste B.V.", new Point(52.37728278446259, 6.599750455072001), 1400, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(12, "Drijvend Zonnepark Lingewaard B.V.", new Point(51.926396040833644, 5.90247713895763), 1000, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(13, "Greenspread Solar I B.V.", new Point(52.065492940898096, 6.610316206618321), 800, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(14, "A - Twence Zon B.V.", new Point(52.226363833536276, 6.797573291421072), 800, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(15, "B - Twence Zon B.V.", new Point(52.22683029803418, 6.7927395185813655), 400, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(16, "Exploitatiemaatschappij Groen Energie B.V.", new Point(52.74410956910856, 5.870474398704344), 800, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(17, "C - Twence Zon B.V.", new Point(52.22584541287809, 6.796815177201207), 600, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(18, "Zonnepark Oosterweilanden B.V.", new Point(52.41019079638181, 6.640535002344596), 400, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(19, "Zonnepark Boldershoek West deel 1", new Point(52.22735862155604, 6.791489702419279), 400, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(20, "Zonnepark Apeldoorn B.V.", new Point(52.24162202575992,  6.008436019765259), 200, SolarPanelType.MONO_CRYSTALLINE));

        solarParks.add(new SolarParkViewModel(21, "Energiepark Zwolle B.V.", new Point(52.50611269144065, 6.136698584064351), 900, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(22, "Zonnepark Flevokust", new Point(52.55976637245377, 5.523806180091385), 2000, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(23, "Zuyderzon Almere B.V.", new Point(52.42021230356996, 5.241693183537771), 1000, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(24, "Flevokustzon B.V.", new Point(52.55802723309972,  5.5208419899146435), 1200, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(25, "Obton Vermunt Solenergi CV", new Point(52.47667620067577, 5.531077684997611), 1500, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(26, "KS NL2 B.V.", new Point(51.914239849001305, 5.899695783618179), 800, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(27, "Zonnepark West Maas en Waal B.V.", new Point(51.872652226395715, 5.499880609795855), 750, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(28, "Zonnepark Fort de Pol B.V.", new Point(52.163940245374015, 6.193199342288541), 500, SolarPanelType.POLY_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(29, "Zonnepark West Maas en Waal B.V.", new Point(51.87211726175407, 5.515936542682456), 100, SolarPanelType.MONO_CRYSTALLINE));
        solarParks.add(new SolarParkViewModel(30, "Rivierenland, Waterschap", new Point(51.965177468519435, 5.854872754972738), 1000, SolarPanelType.POLY_CRYSTALLINE));
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SolarParkProductionRepository solarParkProductionRepository;

    public SimulationSolarService() {
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
        this.simulationLogic = new SimulationLogic();
    }

    public SimulationSolarService(RestTemplate template, HttpHeaders headers) {
        this.template = template;
        this.headers = headers;
        this.simulationLogic = new SimulationLogic();
    }

    //@Scheduled(fixedDelay = 3600000)
    //@Scheduled(fixedDelay = 600000)
    @Scheduled(fixedDelay = 300000)
    private void sendSolarProduction() {
        try {
            var simulationExpectationResult = new SimulationExpectationResult();
            var date = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
            simulationExpectationResult.setCreatedAt(date);

            var simulationResult = new SimulationResult();
            simulationResult.setName("Production");
            List<SimulationResult> results = new ArrayList<>();

            for (var park : solarParks) {
                var data = new FactoryURL().getWeatherData(headers, template, getUrl(park.getCoordinates().getX(), park.getCoordinates().getY()));
                var unit = new SolarUnit(park.getCoordinates(), park.getSolarPanelType(), park.getAmountOfUnits());
                var productionExpectation = simulationLogic.createSimulationForSolarUnit(data.get(0), unit, 20);
                simulationResult.addProductionExpectation(productionExpectation);
                setProduction(park, productionExpectation);
            }
            results.add(simulationResult);
            simulationExpectationResult.setKwTotalResult(simulationLogic.calculateKwProduction(results, true));

            sendProductionResults();
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    //Every Day on 00:00:00 this method will be called
    @Scheduled(cron = "0 0 0 * * ?")
    private void dayReset() {
        try {
            var productions = solarParkProductionRepository.findAll();

            for (var production : productions) {
                var object = solarParkProductionRepository.findSolarParkProductionsBySolarPark_SolarParkId(production.getSolarPark().getSolarParkId());
                if (object != null) {
                    object.setTodayProduction(0);
                    solarParkProductionRepository.save(object);
                }
            }
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    private String getUrl(double x, double y) {
        return "https://api.openweathermap.org/data/2.5/onecall?lat="+x+"&lon="+y+"&exclude=current,minutely,daily,alerts&appid=da713c7b97d2a6f912d9266ec49a30d8";
    }

    private String currentWeatherStringBuilder(Point coordinates) {
        return url + "weather?lat=" + coordinates.getX() + "&lon=" + coordinates.getY() + "&appid=" + apiKey;
    }

    public JsonObject getCurrentWeather(Point coordinates) {
        return getWeatherData(currentWeatherStringBuilder(coordinates)).getAsJsonObject();
    }

    private JsonObject getWeatherData(String url) {
        var builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

    private void setProduction(SolarParkViewModel park, ProductionExpectation productionExpectation) {
        var solarParkProduction = solarParkProductionRepository.findSolarParkProductionsBySolarPark_SolarParkId(park.getSolarParkId());
        if (solarParkProduction == null) {
            solarParkProduction = new SolarParkProduction();
            solarParkProduction.setSolarPark(park);
        }
        solarParkProduction.setTodayProduction(solarParkProduction.getTodayProduction() + (productionExpectation.getKw() / 6));
        solarParkProduction.setYearProduction(solarParkProduction.getYearProduction() + (productionExpectation.getKw() / 6));
        solarParkProductionRepository.save(solarParkProduction);
    }

    private void sendProductionResults() {
        try {
            var results = getOverviewProductionSolarParks();
            var message = objectMapper.writeValueAsString(results);

            rabbitTemplate.convertAndSend("websockets", "websocket.overview.update", message);
        }
        catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    @Override
    public List<SolarParkProduction> getOverviewProductionSolarParks() {
        return solarParkProductionRepository.findAll();
    }
}

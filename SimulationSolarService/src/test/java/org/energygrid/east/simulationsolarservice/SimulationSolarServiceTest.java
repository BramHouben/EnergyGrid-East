package org.energygrid.east.simulationsolarservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;
import org.energygrid.east.simulationsolarservice.service.SimulationSolarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class SimulationSolarServiceTest {

    @Test
    void testSimulationEmptyList() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();

        Assertions.assertNull(simulationSolarService.getSimulationById("123"));
    }

    @Test
    void testAddSimulationCorrect() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();
        SimulationSolar simulationSolar = new SimulationSolar("1");

        simulationSolarService.addSimulation(simulationSolar);

        Assertions.assertEquals(simulationSolar, simulationSolarService.getSimulationById("1"));
    }

    @Test
    void testDeleteSimulation() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();
        SimulationSolar simulationSolar = new SimulationSolar("1");

        simulationSolarService.addSimulation(simulationSolar);

        Assertions.assertEquals(simulationSolar, simulationSolarService.getSimulationById("1"));

        simulationSolarService.deleteSimulation("1");

        Assertions.assertNull(simulationSolarService.getSimulationById("1"));
    }

    @Test
    void testSimulation() {
        SimulationSolarService simulationSolarService = new SimulationSolarService(getWeather());
        
        List<EnergyRegionSolarParksInput> energyRegionSolarParksInputs = new ArrayList<>();
        Point point = new Point(23, 34);
        
        energyRegionSolarParksInputs.add(new EnergyRegionSolarParksInput("test", 23, point));
        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = simulationSolarService.simulateEnergyGrid(energyRegionSolarParksInputs);

        Assertions.assertNotEquals(0, energyRegionSolarParksOutput.getKwhList().size());
    }

    private JsonObject getWeather() {
        final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=51.441642&lon=5.4697225&units=metric&exlude=current,minutely,daily,alerts&appid=d43994b92b8caae6ee650e65194f0ad8";
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        return new Gson().fromJson(responseEntity.getBody(), JsonObject.class);
    }
}

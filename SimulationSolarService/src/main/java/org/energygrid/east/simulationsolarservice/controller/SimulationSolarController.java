package org.energygrid.east.simulationsolarservice.controller;

import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;
import org.energygrid.east.simulationsolarservice.rabbit.RabbitConsumer;
import org.energygrid.east.simulationsolarservice.rabbit.consumer.SolarParkConsumer;
import org.energygrid.east.simulationsolarservice.rabbit.consumer.WeatherConsumer;
import org.energygrid.east.simulationsolarservice.service.ISimulationSolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("simulation")
public class SimulationSolarController {

    @Autowired
    private ISimulationSolarService simulationService;

    @GetMapping("")
    public ResponseEntity<SimulationSolar> getSimulation(@NotNull @RequestParam(name = "id") String id) {
        SimulationSolar simulationSolar = simulationService.getSimulationById(id);

        return ResponseEntity.status(200).body(simulationSolar);
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather() {
        RabbitConsumer<String> rabbitConsumer = new RabbitConsumer<>();
        WeatherConsumer weatherConsumer = new WeatherConsumer();

        String weather = rabbitConsumer.consume(weatherConsumer);

        return ResponseEntity.status(200).body(weather);
    }

    @GetMapping("/solar")
    public ResponseEntity<String> getSolar(@NotNull @RequestParam(name = "name") String name) {
        RabbitConsumer<String> rabbitConsumer = new RabbitConsumer<>();
        SolarParkConsumer solarParkProducer = new SolarParkConsumer(name);

        String jsonSolar = rabbitConsumer.consume(solarParkProducer);

        if(jsonSolar == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(200).body(jsonSolar);
    }

    @GetMapping("/add")
    public ResponseEntity<SimulationSolar> addSimulation() {
        SimulationSolar simulationSolar = new SimulationSolar();
        simulationService.addSimulation(simulationSolar);
        return ResponseEntity.status(200).body(simulationSolar);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSimulation(@RequestParam(name = "id") String id) {
        simulationService.deleteSimulation(id);
        return ResponseEntity.status(200).body("Simulation: " + id + " stopped!");
    }

    @PostMapping("/getLatestSimulation")
    public ResponseEntity<EnergyRegionSolarParksOutput> simulationEnergyGrid(@RequestBody List<EnergyRegionSolarParksInput> energyRegionSolarParksInput) {

        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = simulationService.simulateEnergyGrid(energyRegionSolarParksInput);

        return ResponseEntity.ok().body(energyRegionSolarParksOutput);
    }
}

package org.energygrid.east.simulationservice.controller;

import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationservice.model.Simulation;
import org.energygrid.east.simulationservice.rabbit.RabbitConsumer;
import org.energygrid.east.simulationservice.rabbit.consumer.SolarParkConsumer;
import org.energygrid.east.simulationservice.rabbit.consumer.WeatherConsumer;
import org.energygrid.east.simulationservice.service.ISimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("simulation")
public class SimulationController {

    @Autowired
    private ISimulation simulationService;

    @GetMapping("")
    public ResponseEntity<Simulation> getSimulation(@NotNull @RequestParam(name = "id") String id) {
        Simulation simulation = simulationService.getSimulationById(id);

        return ResponseEntity.status(200).body(simulation);
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
    public ResponseEntity<Simulation> addSimulation() {
        Simulation simulation = new Simulation();
        simulationService.addSimulation(simulation);
        return ResponseEntity.status(200).body(simulation);
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

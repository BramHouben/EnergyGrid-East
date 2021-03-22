package org.energygrid.east.simulationservice.controller;

import org.energygrid.east.simulationservice.model.Simulation;
import org.energygrid.east.simulationservice.rabbit.RabbitProducer;
import org.energygrid.east.simulationservice.rabbit.producer.SolarParkProducer;
import org.energygrid.east.simulationservice.rabbit.producer.WeatherProducer;
import org.energygrid.east.simulationservice.service.ISimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
        RabbitProducer<String> rabbitProducer = new RabbitProducer<>();
        WeatherProducer weatherProducer = new WeatherProducer();

        String weather = rabbitProducer.produce(weatherProducer);

        return ResponseEntity.status(200).body(weather);
    }

    @GetMapping("/solar")
    public ResponseEntity<String> getSolar(@NotNull @RequestParam(name = "name") String name) {
        RabbitProducer<String> rabbitProducer = new RabbitProducer<>();
        SolarParkProducer solarParkProducer = new SolarParkProducer(name);

        String jsonSolar = rabbitProducer.produce(solarParkProducer);

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
}

package org.energygrid.east.solarparkservice.controller;

import org.energygrid.east.solarparkservice.model.Simulation;
import org.energygrid.east.solarparkservice.model.SimulationTimer;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.service.ISimulation;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Timer;

@RestController
@RequestMapping("simulation")
public class SimulationController {

    @Autowired
    private ISolarParkPower solarParkPowerService;

    @Autowired
    private ISimulation simulationService;

    @GetMapping("")
    public ResponseEntity<?> getSimulation(@NotNull @RequestParam(name = "id") String id) {
        Simulation simulation = simulationService.getSimulationById(id);

        return ResponseEntity.status(200).body(simulation);
    }

    @GetMapping("/add")
    public ResponseEntity<?> addSimulation() {
        Timer timer = new Timer();

        SolarPark solarPark = solarParkPowerService.getSolarParkByName("test");
        Simulation simulation = new Simulation(solarPark, timer);
        simulationService.addSimulation(simulation);

        SimulationTimer simulationTimer = new SimulationTimer(simulation);
        timer.scheduleAtFixedRate(simulationTimer, 1000, 1000);

        return ResponseEntity.status(200).body(simulation);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSimulation(@RequestParam(name = "id") String id) {
        Simulation simulation = simulationService.getSimulationById(id);
        simulation.stopTimer();

        return ResponseEntity.status(200).body("Simulation: " + id + " stopped!");
    }
}

package org.energygrid.east.simulationservice.controller;

import org.energygrid.east.simulationservice.model.Simulation;
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

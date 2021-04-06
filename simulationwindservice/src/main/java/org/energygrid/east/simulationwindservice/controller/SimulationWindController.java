package org.energygrid.east.simulationwindservice.controller;

import org.energygrid.east.simulationwindservice.model.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.service.ISimulationWindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("simulation/wind")
public class SimulationWindController {

    @Autowired
    private ISimulationWindService simulationWindService;

    @GetMapping("/create")
    public ResponseEntity<SimulationExpectationResult> createSimulationWindTurbines() {
        return ResponseEntity.ok().body(simulationWindService.createSimulation());
    }
}

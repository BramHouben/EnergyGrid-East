package org.energygrid.east.simulationnuclearservice.controller;

import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.AddNuclearPowerplantDTO;
import org.energygrid.east.simulationnuclearservice.service.ISimulationNuclearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("simulation/nuclear")
public class SimulationNuclearController {

    @Autowired
    private ISimulationNuclearService simulationService;

    @PostMapping("/create")
    public ResponseEntity<Simulation> createNuclearPowerplant(@RequestBody AddNuclearPowerplantDTO addNuclearPowerplantDTO) {
        var simulation = simulationService.addSimulation(addNuclearPowerplantDTO);
        return ResponseEntity.ok().body(simulation);
    }

    @PostMapping("/remove")
    public ResponseEntity removeNuclearPowerPlant(@RequestBody UUID id) {
        if (simulationService.removeSimulation(id)) return ResponseEntity.status(HttpStatus.OK).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Simulation>> getSimulations() {
        return ResponseEntity.ok().body(simulationService.getSimulations());
    }
}
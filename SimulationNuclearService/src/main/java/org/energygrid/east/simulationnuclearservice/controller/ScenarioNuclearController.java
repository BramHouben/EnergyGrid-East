package org.energygrid.east.simulationnuclearservice.controller;

import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.service.IScenarioNuclearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("scenario/nuclear")
public class ScenarioNuclearController {

    @Autowired
    private IScenarioNuclearService scenarioNuclearService;

    @PostMapping("/create")
    public ResponseEntity<Scenario> createScenario(@RequestBody ScenarioDTO scenarioDTO) {
        var scenario = scenarioNuclearService.createScenario(scenarioDTO);
        return ResponseEntity.ok().body(scenario);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Scenario>> getScenarios() {
        var scenarios = scenarioNuclearService.getScenarios();
        return ResponseEntity.ok().body(scenarios);
    }
}

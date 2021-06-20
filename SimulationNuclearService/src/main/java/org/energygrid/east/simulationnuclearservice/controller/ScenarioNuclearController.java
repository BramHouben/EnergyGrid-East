package org.energygrid.east.simulationnuclearservice.controller;

import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.model.results.ScenarioExpectationResult;
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
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody ScenarioDTO scenarioDTO) {

        if (scenarioDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        var scenario = scenarioNuclearService.createScenario(scenarioDTO);
        return ResponseEntity.ok().body(scenario);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScenarioExpectationResult>> getScenarios() {
        var scenarios = scenarioNuclearService.getScenarios();
        return ResponseEntity.ok().body(scenarios);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ScenarioExpectationResult>> getLatestScenarios() {
        var result = scenarioNuclearService.getLatestScenarios();

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }
}

package org.energygrid.east.simulationwindservice.controller;

import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.service.IScenarioWindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("scenario/wind")
public class ScenarioWindController {

    @Autowired
    private IScenarioWindService scenarioWindService;

    @PostMapping("/create")
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody Scenario scenario) {
        var result = scenarioWindService.createScenario(scenario);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ScenarioExpectationResult>> getLatestScenarios() {
        var result = scenarioWindService.getLatestScenarios();
        return ResponseEntity.ok().body(result);
    }
}

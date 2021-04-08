package org.energygrid.east.simulationwindservice.controller;

import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationwindservice.service.IScenarioWindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scenario/wind")
public class ScenarioWindController {

    @Autowired
    private IScenarioWindService scenarioWindService;

    @PostMapping("/create")
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody Scenario scenario) {
        return ResponseEntity.ok().body(scenarioWindService.createScenario(scenario));
    }
}

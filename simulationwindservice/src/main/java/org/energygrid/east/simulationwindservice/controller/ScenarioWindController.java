package org.energygrid.east.simulationwindservice.controller;

import org.energygrid.east.simulationwindservice.model.Scenario;
import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationwindservice.service.IScenarioWindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("scenario/wind")
public class ScenarioWindController {

    @Autowired
    private IScenarioWindService scenarioWindService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody Scenario scenario, @Nullable @RequestParam(name = "times") String[] times) {
        if (times != null) {
            scenario.setWindTurbineOffTimes(Arrays.asList(times));
        }
        var result = scenarioWindService.createScenario(scenario);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ScenarioExpectationResult>> getLatestScenarios() {
        var result = scenarioWindService.getLatestScenarios();
        return ResponseEntity.ok().body(result);
    }
}

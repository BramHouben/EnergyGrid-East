package org.energygrid.east.simulationsolarservice.controller;

import org.energygrid.east.simulationsolarservice.model.Scenario;
import org.energygrid.east.simulationsolarservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationsolarservice.service.IScenarioSolarScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("scenario/solar")
public class ScenarioSolarController {

    @Autowired
    private IScenarioSolarScenario scenarioSolarScenario;

    @PostMapping("/create")
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody Scenario scenario, @Nullable @RequestParam(name = "times") String[] times) {
        if (times != null) {
            scenario.setTurnOffTimes(Arrays.asList(times));
        }
        var result = scenarioSolarScenario.createScenario(scenario);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ScenarioExpectationResult>> getLatestScenarios() {
        var result = scenarioSolarScenario.getLatestScenarios();
        return ResponseEntity.ok().body(result);
    }
}

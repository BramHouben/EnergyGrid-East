package org.energygrid.east.simulationsolarservice.controller;

import org.energygrid.east.simulationsolarservice.model.Scenario;
import org.energygrid.east.simulationsolarservice.model.ScenarioSolarResponse;
import org.energygrid.east.simulationsolarservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationsolarservice.service.IScenarioSolarScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("scenario/solar")
@CrossOrigin(origins = "http://localhost:3000")
public class ScenarioSolarController {

    @Autowired
    private IScenarioSolarScenario scenarioSolarScenario;

    @PostMapping("/create")
    public ResponseEntity<ScenarioExpectationResult> createScenario(@RequestBody Scenario scenario) {
        var result = scenarioSolarScenario.createScenario(scenario);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ScenarioExpectationResult>> getLatestScenarios() {
        var result = scenarioSolarScenario.getLatestScenarios();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/today")
    public ResponseEntity<ScenarioSolarResponse> getTodayScenarios() {
        return ResponseEntity.ok().body(scenarioSolarScenario.countScenariosToday());
    }
}

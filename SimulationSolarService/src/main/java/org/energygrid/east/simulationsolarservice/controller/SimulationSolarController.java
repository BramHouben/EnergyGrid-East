package org.energygrid.east.simulationsolarservice.controller;

import org.energygrid.east.simulationsolarservice.model.ProductionResponse;
import org.energygrid.east.simulationsolarservice.model.SolarParkProduction;
import org.energygrid.east.simulationsolarservice.service.ISimulationSolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("solar/production")
@CrossOrigin(origins = "http://localhost:3000")
public class SimulationSolarController {

    @Autowired
    private ISimulationSolarService simulationSolarService;

    @GetMapping("/overview")
    public ResponseEntity<List<SolarParkProduction>> getProductionOverview() {
        var result = simulationSolarService.getOverviewProductionSolarParks();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/results")
    public ResponseEntity<ProductionResponse> getProductionResultsInNumbers() {
        var today = simulationSolarService.getTodayProduction();
        var annual = simulationSolarService.getYearProduction();
        return ResponseEntity.ok().body(new ProductionResponse(today, annual));
    }
}

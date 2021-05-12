package com.enerygrid.east.energyusageservice.controller;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import com.enerygrid.east.energyusageservice.service.IEnergyUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("usage")
public class EnergyUsageController {

    @Autowired
    private IEnergyUsageService energyUsageService;

    @GetMapping("/day")
    public ResponseEntity<List<EnergyUsage>> getLatestScenarios(@NotNull @RequestParam(name = "date") String date) {
        //Get id with JWT claims when JWT implementation is fully working.
        var result = energyUsageService.getEnergyUsageOfUser("1", date);

        if (result.size() != 24) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(result);
    }
}

package org.energygrid.east.energybalanceservice.controller;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("energybalance")
@RestController
public class EnergyBalanceController {

    @Autowired
    private IEnergyService energyService;

    @GetMapping("/currentbalance")
    public ResponseEntity<EnergyBalance> getCurrentBalance(){

        var energyBalance = energyService.getLatestBalance();
        if (energyBalance==null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(energyBalance);
    }

}

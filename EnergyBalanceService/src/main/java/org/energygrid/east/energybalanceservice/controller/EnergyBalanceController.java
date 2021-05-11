package org.energygrid.east.energybalanceservice.controller;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.service.IEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("energybalance")
@RestController
public class EnergyBalanceController {

    @Autowired
    private IEnergyService energyService;

    @GetMapping("/currentbalance")
    public ResponseEntity<EnergyBalance> getCurrentBalance(){

        EnergyBalance energyBalance = energyService.getLatestBalance();
        if (energyBalance==null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(energyBalance);
    }

//
//    @PutMapping
//    public ResponseEntity<String> updateCurrencyBalance(){
//
//
//    }



}
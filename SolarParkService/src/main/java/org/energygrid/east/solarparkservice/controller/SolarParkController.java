package org.energygrid.east.solarparkservice.controller;


import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("solarpark")
public class SolarParkController {

    @Autowired
    public ISolarParkPower solarParkPowerService;

    @GetMapping("/byId")
    public ResponseEntity<SolarPark> GetSolarParkById( @NotNull @RequestParam(name = "id") int id){
        //Todo something with spring securitt
        boolean doesIdExist = solarParkPowerService.doesIdExist(id);
        if (!doesIdExist){
            return ResponseEntity.badRequest().build();
        }
        SolarPark solarPark= solarParkPowerService.getSolarParkById(id);

        return ResponseEntity.ok().body(solarPark);
    }

    @PostMapping()
    public ResponseEntity<?> AddSolarPark(@NotNull @RequestParam(name = "totalsonarpanels") int totalSonarPanels, @NotNull @RequestParam(name = "name") String name ){

        solarParkPowerService.addSolarPark(totalSonarPanels,name);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping()
    public ResponseEntity<?> DeleteSolarPark(@NotNull @RequestParam(name = "name") String name ){

        solarParkPowerService.removeSolarPark(name);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}

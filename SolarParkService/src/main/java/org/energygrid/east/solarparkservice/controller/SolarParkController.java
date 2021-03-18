package org.energygrid.east.solarparkservice.controller;


import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.model.dto.AddSolarParkDTO;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@RestController
@RequestMapping("solarpark")
public class SolarParkController {

    @Autowired
    private ISolarParkPower solarParkPowerService;

    @GetMapping()
    public ResponseEntity<SolarPark> getSolarParkByName(@NotNull @RequestParam(name = "name") String name) {
        //Todo something with spring security
        boolean doesIdExist = solarParkPowerService.doesNameExist(name);
        if (!doesIdExist) {
            return ResponseEntity.badRequest().build();
        }

        SolarPark solarPark = solarParkPowerService.getSolarParkByName(name);

        return ResponseEntity.ok().body(solarPark);
    }

    @PostMapping()
    public ResponseEntity<String> addSolarPark(@NotNull @RequestBody AddSolarParkDTO solarPark ) {

        solarParkPowerService.addSolarPark(solarPark);

        return ResponseEntity.status(HttpStatus.CREATED).body("Solar park successfully made");

    }

    @PutMapping()
    public ResponseEntity<String> updateSolarPark(@NotNull @RequestParam(name = "name") String name, @NotNull @RequestParam(name = "id") UUID id, @NotNull @RequestParam(name = "solarpanels") int solarpanels) {

        solarParkPowerService.updateSolarPark(id, name, solarpanels);

        return ResponseEntity.status(HttpStatus.OK).body("Solar park successfully updated");

    }

    @DeleteMapping()
    public ResponseEntity<String> deleteSolarPark(@NotNull @RequestParam(name = "name") String name) {

        solarParkPowerService.removeSolarPark(name);

        return ResponseEntity.status(HttpStatus.OK).body("Solar park successfully removed");

    }

}
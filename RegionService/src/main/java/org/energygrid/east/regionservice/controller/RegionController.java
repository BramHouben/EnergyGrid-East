package org.energygrid.east.regionservice.controller;


import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("region")
public class RegionController {

    @Autowired
    IRegionService regionService;

    @GetMapping("provinceinfo")
    public ResponseEntity<List<House>> getProvinceInfo(@RequestParam(name = "regionname") String regionName, @RequestParam(name = "page") long page) {

        if (regionName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<House> houses = regionService.getAllHousesProvince(regionName, page);

        return ResponseEntity.ok().body(houses);


    }

    @GetMapping("cityinfo")
    public ResponseEntity<List<House>> getCityInfo(@RequestParam(name = "cityname") String cityName, @RequestParam(name = "page") long page) {

        if (cityName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<House> houses = regionService.getAllHousesCity(cityName, page);

        return ResponseEntity.ok().body(houses);


    }

    @GetMapping("streetinfo")
    public ResponseEntity<List<House>> getStreetInfo(@RequestParam(name = "streetname") String streetName, @RequestParam(name = "page") long page) {

        if (streetName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<House> houses = regionService.getAllHousesStreet(streetName, page);

        return ResponseEntity.ok().body(houses);


    }
}

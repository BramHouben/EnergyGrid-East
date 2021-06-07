package org.energygrid.east.regionservice.controller;


import org.energygrid.east.regionservice.model.CityInfoRequest;
import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;
import org.energygrid.east.regionservice.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @GetMapping("province/info")
    public ResponseEntity<List<House>> getProvinceInfo(@RequestParam(name = "regionname") String regionName, @RequestParam(name = "page") long page) {

        if (regionName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<House> houses = regionService.getAllHousesProvince(regionName, page);

        return ResponseEntity.ok().body(houses);

    }

    @GetMapping("city/info")
    public ResponseEntity<List<House>> getCityInfo(@RequestParam(name = "cityname") String cityName, @RequestParam(name = "page") long page) {

        if (cityName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<House> houses = regionService.getAllHousesCity(cityName, page);

        return ResponseEntity.ok().body(houses);

    }

    @GetMapping("street/info")
    public ResponseEntity<StreetRequest> getStreetInfo(@RequestParam(name = "streetname") String streetName, @RequestParam(name = "city") String city) {


        if (streetName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var streetRequest = regionService.getAllHousesStreet(streetName, city);

        return ResponseEntity.ok().body(streetRequest);

    }

    @GetMapping("cities/region")
    public ResponseEntity<List<String>> allCitiesRegion(@RequestParam(name = "region") String region) {
        if (region.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<String> cities = regionService.getAllCitiesRegion(region);

        return ResponseEntity.ok().body(cities);

    }

    @GetMapping("city/stats")
    public ResponseEntity<CityInfoRequest> cityInfo(@RequestParam(name = "city") String city) {
        if (city.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        CityInfoRequest cityInfo = regionService.getInfoCity(city);

        return ResponseEntity.ok().body(cityInfo);

    }

}

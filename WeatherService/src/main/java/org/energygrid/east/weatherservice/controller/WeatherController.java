package org.energygrid.east.weatherservice.controller;

import org.energygrid.east.weatherservice.entity.City;
import org.energygrid.east.weatherservice.models.Weather;
import org.energygrid.east.weatherservice.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("weather")
public class WeatherController {

    private final IWeatherService weatherService;

    @Autowired
    public WeatherController(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/current")
    public ResponseEntity<Weather> getCurrentWeather(@NotNull @RequestBody City city ){
        Weather currentWeather = weatherService.getCurrentWeather(city.getCity());

        if(currentWeather == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(currentWeather);
    }
}

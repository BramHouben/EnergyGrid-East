package org.energygrid.east.weatherservice.controller;

import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;
import org.energygrid.east.weatherservice.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private IWeatherService weatherService;

    @PostMapping("/current")
    public ResponseEntity<Weather> getCurrentWeather(@NotNull @RequestBody Coordinates coordinates) {
        Weather currentWeather = weatherService.getCurrentWeather(coordinates);

        if (currentWeather == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(currentWeather);
    }

    @PostMapping("/forecast")
    public ResponseEntity<List<Forecast>> getWeatherForecast(@NotNull @RequestBody Coordinates coordinates) {
        List<Forecast> weatherForecast = weatherService.getWeatherForecast(coordinates);

        if (weatherForecast.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(weatherForecast);
    }
}

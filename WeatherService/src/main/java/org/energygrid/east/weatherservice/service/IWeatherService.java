package org.energygrid.east.weatherservice.service;

import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;

import java.util.List;

public interface IWeatherService {
    Weather getCurrentWeather(Coordinates coordinates);
    List<Forecast> getWeatherForecast(Coordinates coordinates);
}

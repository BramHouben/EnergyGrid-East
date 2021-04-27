package org.energygrid.east.weatherservice.service;

import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;

import java.util.List;

public interface IWeatherService {
    /**
     * @param coordinates of place to get weather
     * @return current weather place
     */
    Weather getCurrentWeather(Coordinates coordinates);

    /**
     * @param coordinates f place to get weather
     * @return forecast weather place
     */
    List<Forecast> getWeatherForecast(Coordinates coordinates);
}

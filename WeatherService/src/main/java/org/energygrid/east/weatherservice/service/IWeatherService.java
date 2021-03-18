package org.energygrid.east.weatherservice.service;

import org.energygrid.east.weatherservice.models.Weather;

public interface IWeatherService {
    Weather getCurrentWeather(String city);
}

package org.energygrid.east.weatherservice.service;

import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;

import java.awt.geom.Point2D;
import java.util.List;

public interface IWeatherService {
    Weather getCurrentWeather(Point2D.Double coordinates);
    List<Forecast> getWeatherForecast(Point2D.Double coordinates);
}

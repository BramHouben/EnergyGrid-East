package org.energygrid.east.weatherservice.service;

import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Weather;

import java.awt.*;
import java.awt.geom.Point2D;

public interface IWeatherService {
    Weather getCurrentWeather(Point2D.Double coordinates);
}

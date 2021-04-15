package org.energygrid.east.weatherservice;

import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;
import org.energygrid.east.weatherservice.service.WeatherService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.awt.geom.Point2D;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
class OpenWeatherMapIntegrationTests {
    private final WeatherService weatherService;

    OpenWeatherMapIntegrationTests() {
        weatherService = new WeatherService();
    }

    @Test
    void getCurrentWeatherTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);
        Coordinates coordinates = new Coordinates(coordinate);

        Weather weather = weatherService.getCurrentWeather(coordinates);

        Assert.assertNotNull(weather);
        Assert.assertEquals(3, weather.getSymbol().length());
    }

    @Test
    void getWeatherForecastTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);
        Coordinates coordinates = new Coordinates(coordinate);

        List<Forecast> forecasts = weatherService.getWeatherForecast(coordinates);

        Assert.assertEquals(7, forecasts.size());
    }
}

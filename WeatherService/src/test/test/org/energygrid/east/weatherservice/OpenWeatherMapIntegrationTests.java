package org.energygrid.east.weatherservice;

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
public class OpenWeatherMapIntegrationTests {
    private final WeatherService weatherService;

    public OpenWeatherMapIntegrationTests() {
        weatherService = new WeatherService();
    }

    @Test
    public void getCurrentWeatherTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        Weather weather = weatherService.getCurrentWeather(coordinate);

        Assert.assertNotNull(weather);
        Assert.assertEquals(3, weather.getSymbol().length());
    }

    @Test
    public void getWeatherForecastTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        List<Forecast> forecasts = weatherService.getWeatherForecast(coordinate);

        Assert.assertEquals(7, forecasts.size());
    }
}

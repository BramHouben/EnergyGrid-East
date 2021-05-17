package org.energygrid.east.weatherservice;

import org.energygrid.east.weatherservice.service.WeatherService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.awt.geom.Point2D;

@ActiveProfiles("test")
@SpringBootTest
class WeatherServiceTests {

    @Autowired
    private WeatherService weatherService;

    @Value("${APIKEY}")
    private String apiKey;


    @Test
    void convertKelvinToCelsiusTest() {
        Assert.assertEquals(2, weatherService.kelvinToCelsius(275.15), 0.001);
    }

    @Test
    void convertKelvinToCelsiusTestNegative() {
        Assert.assertEquals(-2, weatherService.kelvinToCelsius(271.15), 0.001);
    }

    @Test
    void convertKelvinToCelsiusTestDouble() {
        Assert.assertEquals(26.85, weatherService.kelvinToCelsius(300), 0.001);
    }

    @Test
    void convertUnixToDate() {
        Assert.assertEquals("12-04", weatherService.formatDate(1618225200));
    }

    @Test
    void currentWeatherStringBuilderTest() {
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        Assert.assertEquals("https://api.openweathermap.org/data/2.5/weather?lat=52.57768011883653&lon=5.531332567397516&appid=" + apiKey, weatherService.currentWeatherStringBuilder(coordinate));
    }

    @Test
    void weatherForecastStringBuilderTest() {
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        Assert.assertEquals("https://api.openweathermap.org/data/2.5/onecall?lat=52.57768011883653&lon=5.531332567397516&exclude=current,minutely,hourly,alerts&appid=" + apiKey, weatherService.weatherForecastStringBuilder(coordinate));
    }
}
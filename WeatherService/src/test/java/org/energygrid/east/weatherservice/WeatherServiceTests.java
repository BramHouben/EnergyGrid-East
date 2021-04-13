package java.org.energygrid.east.weatherservice;

import org.energygrid.east.weatherservice.service.WeatherService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.awt.geom.Point2D;

@ActiveProfiles("test")
@SpringBootTest
public class WeatherServiceTests {
    private final WeatherService weatherService;

    public WeatherServiceTests() {
        weatherService = new WeatherService();
    }

    @Test
    public void convertKelvinToCelsiusTest(){
        Assert.assertEquals(2, weatherService.kelvinToCelsius(275.15), 0.001);
    }

    @Test
    public void convertKelvinToCelsiusTestNegative(){
        Assert.assertEquals(-2, weatherService.kelvinToCelsius(271.15), 0.001);
    }

    @Test
    public void convertKelvinToCelsiusTestDouble(){
        Assert.assertEquals(26.85, weatherService.kelvinToCelsius(300), 0.001);
    }

    @Test
    public void convertUnixToDate(){
        Assert.assertEquals("12-04", weatherService.formatDate(1618225200));
    }

    @Test
    public void currentWeatherStringBuilderTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        Assert.assertEquals("https://api.openweathermap.org/data/2.5/weather?lat=52.57768011883653&lon=5.531332567397516&appid=d43994b92b8caae6ee650e65194f0ad8", weatherService.currentWeatherStringBuilder(coordinate));
    }

    @Test
    public void weatherForecastStringBuilderTest(){
        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);

        Assert.assertEquals("https://api.openweathermap.org/data/2.5/onecall?lat=52.57768011883653&lon=5.531332567397516&exclude=current,minutely,hourly,alerts&appid=d43994b92b8caae6ee650e65194f0ad8", weatherService.weatherForecastStringBuilder(coordinate));
    }
}

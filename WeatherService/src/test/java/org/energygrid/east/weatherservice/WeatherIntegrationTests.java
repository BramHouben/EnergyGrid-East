package org.energygrid.east.weatherservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.energygrid.east.weatherservice.controller.WeatherController;
import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;
import org.energygrid.east.weatherservice.service.IWeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherIntegrationTests {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private IWeatherService weatherService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    public void returnOkGetCurrentWeather() throws Exception {

        final Weather currentWeather = new Weather(15, "10d");

        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);
        Coordinates coordinates = new Coordinates(coordinate);

        when(weatherService.getCurrentWeather(any(Coordinates.class))).thenReturn(currentWeather);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(coordinates);

        mockMvc.perform(post("/weather/current").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBadRequestGetCurrentWeather() throws Exception {

        Point2D.Double coordinate = new Point2D.Double(0, 0);
        Coordinates coordinates = new Coordinates(coordinate);

        when(weatherService.getCurrentWeather(coordinates)).thenReturn(null);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(coordinates);

        mockMvc.perform(post("/weather/current").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnOkGetForecast() throws Exception {

        List<Forecast> weekForecast = new ArrayList<>();
        weekForecast.add(new Forecast(0.1, 1.1, "10d", "01-01", 13.42, 360, 15));

        Point2D.Double coordinate = new Point2D.Double(52.57768011883653, 5.531332567397516);
        Coordinates coordinates = new Coordinates(coordinate);

        when(weatherService.getWeatherForecast(any(Coordinates.class))).thenReturn(weekForecast);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(coordinates);

        mockMvc.perform(post("/weather/forecast").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBadRequestGetForecast() throws Exception {

        Point2D.Double coordinate = new Point2D.Double(0, 0);
        Coordinates coordinates = new Coordinates(coordinate);

        when(weatherService.getWeatherForecast(coordinates)).thenReturn(null);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(coordinates);

        mockMvc.perform(post("/weather/forecast").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}

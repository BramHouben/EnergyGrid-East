package org.energygrid.east.weatherservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

@Service
public class WeatherService implements IWeatherService {

    private String url = "https://api.openweathermap.org/data/2.5/weather";
    private String apiKey = "d43994b92b8caae6ee650e65194f0ad8";
    private RestTemplate template;
    private HttpHeaders headers;

    @Autowired
    public WeatherService() {
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    private JsonObject getCurrentWeatherData(Point2D.Double coordinates){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "?lat=" + coordinates.getX() + "&lon=" + coordinates.getY()+ "&appid=" + apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

    private double kelvinToCelsius(double kelvin){
        return kelvin - 273.15;
    }

    @Override
    public Weather getCurrentWeather(Point2D.Double coordinates) {
        var fullWeather = getCurrentWeatherData(coordinates).getAsJsonObject();

        var symbol = fullWeather.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
        var tempKelvin = fullWeather.get("main").getAsJsonObject().getAsJsonObject().get("temp").getAsDouble();

        var temp = Math.round(kelvinToCelsius(tempKelvin));

        return new Weather(temp, symbol);
    }
}

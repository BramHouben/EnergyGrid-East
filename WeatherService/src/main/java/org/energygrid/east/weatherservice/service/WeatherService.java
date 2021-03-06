package org.energygrid.east.weatherservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.energygrid.east.weatherservice.entity.Coordinates;
import org.energygrid.east.weatherservice.models.Forecast;
import org.energygrid.east.weatherservice.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeatherService implements IWeatherService {

    @Value("${URL}")
    private String url ;
    @Value("${APIKEY}")
    private String apiKey;

    private final RestTemplate template;
    private final HttpHeaders headers;

    @Autowired
    public WeatherService() {
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    private JsonObject getWeatherData(String url) {
        var builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

    public String currentWeatherStringBuilder(Point2D.Double coordinates) {
        return url + "weather?lat=" + coordinates.getX() + "&lon=" + coordinates.getY() + "&appid=" + apiKey;
    }

    public String weatherForecastStringBuilder(Point2D.Double coordinates) {
        return url + "onecall?lat=" + coordinates.getX() + "&lon=" + coordinates.getY() + "&exclude=current,minutely,hourly,alerts" + "&appid=" + apiKey;
    }

    public double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public String formatDate(long unix) {
        var date = new Date(unix * 1000);
        var format = new SimpleDateFormat("dd-MM");

        return format.format(date);
    }

    @Override
    public Weather getCurrentWeather(Coordinates coordinates) {
        var fullWeather = getWeatherData(currentWeatherStringBuilder(coordinates.getCoordinates())).getAsJsonObject();

        var symbol = fullWeather.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
        var tempKelvin = fullWeather.get("main").getAsJsonObject().getAsJsonObject().get("temp").getAsDouble();

        var temp = Math.round(kelvinToCelsius(tempKelvin));

        return new Weather(temp, symbol);
    }

    @Override
    public List<Forecast> getWeatherForecast(Coordinates coordinates) {
        var fullWeatherForecast = getWeatherData(weatherForecastStringBuilder(coordinates.getCoordinates())).getAsJsonObject();

        var forecastData = fullWeatherForecast.get("daily").getAsJsonArray();
        forecastData.remove(forecastData.size() - 1);

        List<Forecast> weekForecast = new ArrayList<>();

        for (JsonElement day : forecastData) {
            double minTemp = Math.round(kelvinToCelsius(day.getAsJsonObject().get("temp").getAsJsonObject().get("min").getAsDouble()));
            double maxTemp = Math.round(kelvinToCelsius(day.getAsJsonObject().get("temp").getAsJsonObject().get("max").getAsDouble()));

            var icon = day.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
            String date = formatDate(day.getAsJsonObject().get("dt").getAsLong());

            var windSpeed = day.getAsJsonObject().get("wind_speed").getAsDouble();
            int windDirection = day.getAsJsonObject().get("wind_deg").getAsInt() + 90;
            int sunPercentage = 100 - day.getAsJsonObject().get("clouds").getAsInt();

            weekForecast.add(new Forecast(minTemp, maxTemp, icon, date, windSpeed, windDirection, sunPercentage));
        }

        return weekForecast;
    }
}

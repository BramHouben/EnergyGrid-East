package org.energygrid.east.weatherservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.energygrid.east.weatherservice.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService implements IWeatherService {

    private String url = "http://weerlive.nl/api/json-data-10min.php?key=833b9517d6";
    private RestTemplate template;
    private HttpHeaders headers;

    @Autowired
    public WeatherService() {
        this.template = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Override
    public Weather getCurrentWeather(String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "&locatie=" + city);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        JsonObject obj = new Gson().fromJson(response.getBody(), JsonObject.class);

        var weather = obj.getAsJsonObject().get("liveweer").getAsJsonArray().get(0);
        var temp = weather.getAsJsonObject().get("temp").getAsDouble();
        var symbol = weather.getAsJsonObject().get("image").getAsString();

        return new Weather(city, temp, symbol);
    }
}

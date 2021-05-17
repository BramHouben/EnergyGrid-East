package org.energygrid.east.simulationsolarservice.factory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
public class FactoryURL {

    public JsonArray getWeatherData(HttpHeaders headers, RestTemplate template, String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        var weatherData = new Gson().fromJson(response.getBody(), JsonObject.class);
        return weatherData.getAsJsonObject().get("hourly").getAsJsonArray();
    }
}

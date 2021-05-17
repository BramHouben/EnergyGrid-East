package org.energygrid.east.weatherservice.rabbit.timer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WeatherTimer extends TimerTask {
    private static final Logger logger = Logger.getLogger(WeatherTimer.class.getName());
    @Value("${APIKEY}")
    private String apiKey;
    private final Channel channel;
    private final String exchange_name;

    private boolean started;

    public WeatherTimer(Channel channel, String exchange_name) {
        started = false;
        this.channel = channel;
        this.exchange_name = exchange_name;
    }

    @Override
    public void run() {
        this.started = true;
        try {
            var jsonObject = getWeather();

            channel.basicPublish(exchange_name, "", null, new Gson().toJson(jsonObject).getBytes());
            logger.log(Level.ALL, "weather published");
        } catch (IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }

    private JsonObject getWeather() {
        final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=51.441642&lon=5.4697225&units=metric&exlude=current,minutely,daily,alerts&appid="+apiKey;
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

        return new Gson().fromJson(responseEntity.getBody(), JsonObject.class);
    }

    public boolean isStarted() {
        return started;
    }
}

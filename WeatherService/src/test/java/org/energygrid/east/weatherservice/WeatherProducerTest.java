//package org.energygrid.east.weatherservice;
//
//import com.rabbitmq.client.Channel;
//import org.energygrid.east.weatherservice.rabbit.RabbitConfiguration;
//import org.energygrid.east.weatherservice.rabbit.timer.WeatherTimer;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import static org.junit.jupiter.api.Assertions.*;
//import java.io.IOException;
//import java.util.Timer;
//import java.util.concurrent.TimeoutException;
//
//@ActiveProfiles("test")
//@SpringBootTest
//class WeatherProducerTest {
//
////    @Test
////    void testTimerState() throws IOException, TimeoutException {
////
////        Channel channel = RabbitConfiguration.getInstance().getChannel();
////        TimerTask weatherTimer = new WeatherTimer(channel, "weather_exchange");
////        weatherTimer.run();
////        WeatherTimer w = (WeatherTimer) weatherTimer;
////
////        Assertions.assertTrue(w.isStarted());
////    }
//
////    @Test
////    void testTimer() throws InterruptedException, IOException, TimeoutException {
////
////        Channel channel = RabbitConfiguration.getInstance().getChannel();
////        WeatherTimer weatherTimer = new WeatherTimer(channel, "weather_exchange");
////        Timer timer = new Timer();
////        timer.schedule(weatherTimer, 0);
////
////        Thread.sleep(1000);
////
////        assertTrue(weatherTimer.isStarted());
////    }
//}

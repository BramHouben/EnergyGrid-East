package com.example.websocketsservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class WebsocketsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketsServiceApplication.class, args);
    }


    @Autowired
  private   RabbitTemplate rabbitTemplate;

    @Bean
    @Scheduled(fixedDelay = 5000)
     void tests(){
        var message  = "test";
        rabbitTemplate.convertAndSend("WebSockets", "websockets.balance.update", message);    }

}

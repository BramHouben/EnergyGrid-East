package org.energygrid.east.authenticationservice.controller;

import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.UserConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class Startup implements ApplicationRunner {
    public Startup() {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RabbitConsumer rabbitConsumer = new RabbitConsumer();
        rabbitConsumer.consume(new UserConsumer());
    }
}

package org.energygrid.east.authenticationservice.controller;

import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.AddUserConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.UpdateUserConsumer;
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
    }
}

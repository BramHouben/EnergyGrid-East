package org.energygrid.east.solarparkservice.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitListener implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("wdwedw");

        return;
    }
}

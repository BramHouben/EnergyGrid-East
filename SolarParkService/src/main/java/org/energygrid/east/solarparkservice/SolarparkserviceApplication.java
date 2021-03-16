package org.energygrid.east.solarparkservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SolarparkserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolarparkserviceApplication.class, args);
    }
}

package org.energygrid.east.solarparkservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SolarparkserviceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SolarparkserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("adwedewd");
    }

    @Bean
    public CommandLineRunner test() {
        return (args) -> {
            System.out.println("wwwewewew");
        };
    }
}

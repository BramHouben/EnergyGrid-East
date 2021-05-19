package org.energygrid.east.simulationsolarservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimulationSolarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulationSolarServiceApplication.class, args);
    }

}

package org.energygrid.east.simulationwindservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimulationWindServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulationWindServiceApplication.class, args);
    }

}

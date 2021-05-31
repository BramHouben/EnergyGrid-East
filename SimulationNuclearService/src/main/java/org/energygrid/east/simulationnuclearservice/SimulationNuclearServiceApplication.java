package org.energygrid.east.simulationnuclearservice;

import org.energygrid.east.simulationnuclearservice.repository.SimulationNuclearRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SimulationNuclearRepository.class)
@SpringBootApplication
public class SimulationNuclearServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulationNuclearServiceApplication.class, args);
    }

}

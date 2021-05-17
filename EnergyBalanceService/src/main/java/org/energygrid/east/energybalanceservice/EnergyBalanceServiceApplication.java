package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableMongoRepositories(basePackageClasses = EnergyBalanceRepo.class)
@EnableScheduling
@SpringBootApplication
public class EnergyBalanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyBalanceServiceApplication.class, args);
    }

}

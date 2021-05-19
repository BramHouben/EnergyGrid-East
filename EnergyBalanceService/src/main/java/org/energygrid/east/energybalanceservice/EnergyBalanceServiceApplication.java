package org.energygrid.east.energybalanceservice;

import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@EnableMongoRepositories(basePackageClasses = EnergyBalanceRepo.class)
@EnableScheduling
@SpringBootApplication
public class EnergyBalanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyBalanceServiceApplication.class, args);
    }

}

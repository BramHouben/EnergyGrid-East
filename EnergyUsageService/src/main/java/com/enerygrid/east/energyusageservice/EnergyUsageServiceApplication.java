package com.enerygrid.east.energyusageservice;

import com.enerygrid.east.energyusageservice.repository.EnergyUsageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableMongoRepositories(basePackageClasses = EnergyUsageRepository.class)
@SpringBootApplication
public class EnergyUsageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUsageServiceApplication.class, args);
    }
}

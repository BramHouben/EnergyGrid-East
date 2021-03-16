package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.repo.ISolarParkRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = ISolarParkRepo.class)
@SpringBootApplication
public class SolarparkserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolarparkserviceApplication.class, args);
    }
}

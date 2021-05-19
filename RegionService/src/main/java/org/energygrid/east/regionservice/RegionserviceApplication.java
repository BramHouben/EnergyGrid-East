package org.energygrid.east.regionservice;

import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@EnableMongoRepositories(basePackageClasses = IRegionRepo.class)
@SpringBootApplication
public class RegionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionserviceApplication.class, args);
    }

}

package com.enerygrid.east.energyusageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EnergyUsageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUsageServiceApplication.class, args);
    }
}

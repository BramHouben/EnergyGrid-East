package com.enerygrid.east.energyusageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class EnergyUsageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUsageServiceApplication.class, args);
    }
}

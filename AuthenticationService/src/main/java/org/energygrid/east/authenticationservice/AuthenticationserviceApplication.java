package org.energygrid.east.authenticationservice;

import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableJpaRepositories(basePackageClasses = AuthenticationRepository.class)
@SpringBootApplication
public class AuthenticationserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationserviceApplication.class, args);
    }

}

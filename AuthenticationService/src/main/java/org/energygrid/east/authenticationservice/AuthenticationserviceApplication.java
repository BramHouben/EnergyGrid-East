package org.energygrid.east.authenticationservice;

import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.energygrid.east.authenticationservice.threads.RabbitMqAddUserThread;
import org.energygrid.east.authenticationservice.threads.RabbitMqDeleteUserThread;
import org.energygrid.east.authenticationservice.threads.RabbitMqUpdateUserThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableJpaRepositories(basePackageClasses = AuthenticationRepository.class)
@SpringBootApplication
public class AuthenticationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationserviceApplication.class, args);
	}

}

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
		Executor executor = Executors.newScheduledThreadPool(2);

		var addUserThread = new RabbitMqAddUserThread();
		executor.execute(addUserThread);

		var updateUserThread = new RabbitMqUpdateUserThread();
		executor.execute(updateUserThread);

		var deleteUserThread = new RabbitMqDeleteUserThread();
		executor.execute(deleteUserThread);

		SpringApplication.run(AuthenticationserviceApplication.class, args);
	}

}

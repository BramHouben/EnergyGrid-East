package org.energygrid.east.authenticationservice;

import org.energygrid.east.authenticationservice.threads.RabbitMqAddUserThread;
import org.energygrid.east.authenticationservice.threads.RabbitMqDeleteUserThread;
import org.energygrid.east.authenticationservice.threads.RabbitMqUpdateUserThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Profile("!test")
@Component
public class Startup implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Executor executor = Executors.newScheduledThreadPool(3);

        var addUserThread = new RabbitMqAddUserThread();
        executor.execute(addUserThread);

        var updateUserThread = new RabbitMqUpdateUserThread();
        executor.execute(updateUserThread);

        var deleteUserThread = new RabbitMqDeleteUserThread();
        executor.execute(deleteUserThread);

    }
}

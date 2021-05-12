package org.energygrid.east.authenticationservice.threads;

import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.AddUserConsumer;

public class RabbitMqAddUserThread implements Runnable {
    private final RabbitConsumer rabbitConsumer;
    private final AddUserConsumer addUserConsumer;

    public RabbitMqAddUserThread() {
        rabbitConsumer = new RabbitConsumer();
        addUserConsumer = new AddUserConsumer();
    }

    @Override
    public void run() {
        rabbitConsumer.consume(addUserConsumer);
    }
}

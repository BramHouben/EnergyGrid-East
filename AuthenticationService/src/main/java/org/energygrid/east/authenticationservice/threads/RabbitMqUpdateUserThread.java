package org.energygrid.east.authenticationservice.threads;

import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.UpdateUserConsumer;

public class RabbitMqUpdateUserThread implements Runnable {
    private final RabbitConsumer rabbitConsumer;
    private final UpdateUserConsumer updateUserConsumer;

    public RabbitMqUpdateUserThread() {
        rabbitConsumer = new RabbitConsumer();
        updateUserConsumer = new UpdateUserConsumer();
    }

    @Override
    public void run() {
        rabbitConsumer.consume(updateUserConsumer);
    }
}

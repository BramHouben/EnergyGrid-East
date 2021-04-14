package org.energygrid.east.authenticationservice.threads;

import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.DeleteUserConsumer;
import org.energygrid.east.authenticationservice.rabbit.consumer.UpdateUserConsumer;

public class RabbitMqDeleteUserThread implements Runnable {
    private RabbitConsumer rabbitConsumer;
    private DeleteUserConsumer deleteUserConsumer;

    public RabbitMqDeleteUserThread() {
        rabbitConsumer = new RabbitConsumer();
        deleteUserConsumer = new DeleteUserConsumer();
    }

    @Override
    public void run() {
        rabbitConsumer.consume(deleteUserConsumer);
    }
}

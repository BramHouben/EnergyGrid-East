//package org.energygrid.east.authenticationservice.threads;
//
//import org.energygrid.east.authenticationservice.rabbit.RabbitConsumer;
//import org.energygrid.east.authenticationservice.rabbit.consumer.DeleteUserConsumer;
//
//public class RabbitMqDeleteUserThread implements Runnable {
//    private final RabbitConsumer rabbitConsumer;
//    private final DeleteUserConsumer deleteUserConsumer;
//
//    public RabbitMqDeleteUserThread() {
//        rabbitConsumer = new RabbitConsumer();
//        deleteUserConsumer = new DeleteUserConsumer();
//    }
//
//    @Override
//    public void run() {
//        rabbitConsumer.consume(deleteUserConsumer);
//    }
//}

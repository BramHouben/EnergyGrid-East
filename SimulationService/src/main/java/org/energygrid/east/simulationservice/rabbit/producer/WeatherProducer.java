package org.energygrid.east.simulationservice.rabbit.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import org.energygrid.east.simulationservice.rabbit.Producer;
import org.energygrid.east.simulationservice.rabbit.RabbitConfig;
import org.energygrid.east.simulationservice.rabbit.consumer.DefaultRabbitRPCConsumer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WeatherProducer implements Producer<String> {

    private final RabbitConfig rabbitConfig;
    private final String corrId;
    private final String REQUEST_QUEUE_NAME;

    public WeatherProducer() {
        rabbitConfig = RabbitConfig.getInstance();
        corrId = UUID.randomUUID().toString();
        REQUEST_QUEUE_NAME = "weatherservice_queue";
    }

    @Override
    public String produce(Channel channel) {
        try {
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties properties = rabbitConfig.getProperties(corrId, replyQueueName);

            BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
            Consumer consumer = new DefaultRabbitRPCConsumer(channel, blockingQueue, corrId);

            channel.basicConsume(replyQueueName, true, consumer);
            channel.basicPublish("", REQUEST_QUEUE_NAME, properties, "give me the weather".getBytes());

            return blockingQueue.poll(3000, TimeUnit.MILLISECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}

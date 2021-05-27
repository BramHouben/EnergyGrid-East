package org.energygrid.east.energybalanceservice.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class RabbitConfig {

    static final String TOPIC_EXCHANGE = "EnergyBalance";
    static final String QUEUE_NAME_WIND = "energy-balance-wind";
    static final String QUEUE_NAME_SOLAR = "energy-balance-solar";
    static final String QUEUE_NAME_NUCLEAR = "energy-balance-nuclear";
    static final String QUEUE_NAME_USAGE = "energy-balance-usage";


    @Bean
    @Qualifier("queueNameWind")
    Queue queueWind() {
        return new Queue(QUEUE_NAME_WIND, false);
    }

    @Bean
    @Qualifier("queueNameSolar")
    Queue queueSolar() {
        return new Queue(QUEUE_NAME_SOLAR, false);
    }

    @Bean
    @Qualifier("queueNameNuclear")
    Queue queueNuclear() {
        return new Queue(QUEUE_NAME_NUCLEAR, false);
    }

    @Bean
    @Qualifier("queueNameUsage")
    Queue queueHouse() {
        return new Queue(QUEUE_NAME_USAGE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding bindingSolar(@Qualifier("queueNameSolar") Queue queueNameSolar, TopicExchange exchange) {
        return BindingBuilder.bind(queueNameSolar).to(exchange).with("balance.create.solar");
    }

    @Bean
    Binding bindingNuclear(@Qualifier("queueNameNuclear") Queue queueNameNuclear, TopicExchange exchange) {
        return BindingBuilder.bind(queueNameNuclear).to(exchange).with("balance.create.nuclear");
    }

    @Bean
    Binding bindingWind(@Qualifier("queueNameWind") Queue queueNameWind, TopicExchange exchange) {
        return BindingBuilder.bind(queueNameWind).to(exchange).with("balance.create.wind");
    }

    @Bean
    Binding bindingHouse(@Qualifier("queueNameUsage") Queue queueNameUsage, TopicExchange exchange) {
        return BindingBuilder.bind(queueNameUsage).to(exchange).with("balance.create.usage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapterWind") MessageListenerAdapter listenerAdapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_WIND);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerSolar(ConnectionFactory connectionFactory,
                                                  @Qualifier("listenerAdapterSolar") MessageListenerAdapter listenerAdapterSolar) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_SOLAR);
        container.setMessageListener(listenerAdapterSolar);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerNuclear(ConnectionFactory connectionFactory,
                                                    @Qualifier("listenerAdapterNuclear") MessageListenerAdapter listenerAdapterNuclear) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_NUCLEAR);
        container.setMessageListener(listenerAdapterNuclear);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerUsage(ConnectionFactory connectionFactory,
                                                    @Qualifier("listenerAdapterUsage") MessageListenerAdapter listenerAdapterNuclear) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_USAGE);
        container.setMessageListener(listenerAdapterNuclear);
        return container;
    }

    @Bean
    @Qualifier("listenerAdapterWind")
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageWind");
    }

    @Bean
    @Qualifier("listenerAdapterSolar")
    MessageListenerAdapter listenerAdapterSolar(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageSolar");
    }

    @Bean
    @Qualifier("listenerAdapterNuclear")
    MessageListenerAdapter listenerAdapterNuclear(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageNuclear");
    }

    @Bean
    @Qualifier("listenerAdapterUsage")
    MessageListenerAdapter listenerAdapterUsage(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageUsage");
    }
}
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

    static final String topicExchangeName = "EnergyBalance";
    static final String queueNameWind = "energy-balance-wind";
    static final String queueNameSolar = "energy-balance-solar";
    static final String queueNameNuclear = "energy-balance-nuclear";


    @Bean
    @Qualifier("queueNameWind")
    Queue queueWind() {
        return new Queue(queueNameWind, false);
    }

    @Bean
    @Qualifier("queueNameSolar")
    Queue queueSolar() {
        return new Queue(queueNameSolar, false);
    }

    @Bean
    @Qualifier("queueNameNuclear")
    Queue queueNuclear() {
        return new Queue(queueNameNuclear, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
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
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             @Qualifier("listenerAdapterWind") MessageListenerAdapter listenerAdapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNameWind);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerSolar(ConnectionFactory connectionFactory,
                                                  @Qualifier("listenerAdapterSolar") MessageListenerAdapter listenerAdapterSolar) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNameSolar);
        container.setMessageListener(listenerAdapterSolar);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerNuclear(ConnectionFactory connectionFactory,
                                                  @Qualifier("listenerAdapterNuclear") MessageListenerAdapter listenerAdapterNuclear) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNameNuclear);
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
}

package org.energygrid.east.websocketsservice.rabbit;

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

    static final String WEBSOCKET_TOPIC = "websockets";
    static final String BALANCE_QUEUE = "balance-queue";
    static final String OVERVIEW_PRODUCTION_QUEUE = "overview-production-queue";

    @Bean
    Queue queue() {
        return new Queue(BALANCE_QUEUE, false);
    }

    @Bean
    @Qualifier("queueNameOverviewProduction")
    Queue queueOverviewProduction() {
        return new Queue(OVERVIEW_PRODUCTION_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(WEBSOCKET_TOPIC);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("websocket.balance.update");
    }

    @Bean
    Binding bindingOverviewProduction(@Qualifier("queueNameOverviewProduction") Queue queueOverviewProduction, TopicExchange exchange) {
        return BindingBuilder.bind(queueOverviewProduction).to(exchange).with("websocket.overview.update");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BALANCE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerOverviewProduction(ConnectionFactory connectionFactory,
                                                               @Qualifier("listenerAdapterOverviewProduction") MessageListenerAdapter listenerAdapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(OVERVIEW_PRODUCTION_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "getBalance");
    }

    @Bean
    @Qualifier("listenerAdapterOverviewProduction")
    MessageListenerAdapter listenerAdapterOverviewProduction(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveOverviewProduction");
    }
}

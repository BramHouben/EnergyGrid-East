package org.energygrid.east.solarparkservice.rabbit.rabbitv2;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class RabbitConfigV2 {

    static final String DIRECT_EXCHANGE_NAME = "SolarPanels";
    static final String QUEUE_NAME_SOLAR_PANELS = "solar-panels-get";

    @Bean
    Queue queueSolarPanels() {
        return new Queue(QUEUE_NAME_SOLAR_PANELS, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Binding bindingSolar(Queue queueNameSolarPanels, DirectExchange directExchangeName) {
        return BindingBuilder.bind(queueNameSolarPanels).to(directExchangeName).with("solar.panels.get");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME_SOLAR_PANELS);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageParks");
    }


}

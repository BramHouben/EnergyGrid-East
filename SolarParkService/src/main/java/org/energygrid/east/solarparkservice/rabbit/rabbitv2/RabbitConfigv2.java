package org.energygrid.east.solarparkservice.rabbit.rabbitv2;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class RabbitConfigv2 {

    static final String directExchangeName = "SolarPanels";
    static final String queueNameSolarPanels = "solar-panels-get";

    @Bean
    Queue queueSolarPanels() {
        return new Queue(queueNameSolarPanels, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
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
        container.setQueueNames(queueNameSolarPanels);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageParks");
    }


}

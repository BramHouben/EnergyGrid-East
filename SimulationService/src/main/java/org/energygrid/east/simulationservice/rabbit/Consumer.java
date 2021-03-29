package org.energygrid.east.simulationservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer<T> {

    T consume(Channel channel);
}

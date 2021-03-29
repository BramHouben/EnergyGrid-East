package org.energygrid.east.simulationservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Producer<T> {

    T produce(Channel channel);
}

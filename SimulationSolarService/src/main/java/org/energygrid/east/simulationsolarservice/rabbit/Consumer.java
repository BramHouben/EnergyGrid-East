package org.energygrid.east.simulationsolarservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer<T> {

    T consume(Channel channel);
}

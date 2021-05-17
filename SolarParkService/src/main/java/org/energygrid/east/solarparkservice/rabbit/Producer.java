package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Producer<T> {

    void produce(Channel channel);
}

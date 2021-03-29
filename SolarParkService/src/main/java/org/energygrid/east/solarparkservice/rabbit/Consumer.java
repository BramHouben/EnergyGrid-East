package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer<T> {

    void consume(Channel channel);
}

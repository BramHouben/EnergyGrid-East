package org.energygrid.east.authenticationservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer<T> {

    T consume(Channel channel);
}


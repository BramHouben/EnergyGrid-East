package org.energygrid.east.authenticationservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer {

    void consume(Channel channel);
}

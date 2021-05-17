package org.energygrid.east.userservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer {

    void consume(Channel channel);
}

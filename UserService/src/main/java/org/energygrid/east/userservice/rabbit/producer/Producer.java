package org.energygrid.east.userservice.rabbit.producer;

import com.rabbitmq.client.Channel;

public interface Producer {
    void produce(Channel channel);
}

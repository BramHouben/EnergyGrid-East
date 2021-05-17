package org.energygrid.east.userservice.rabbit.Producer;

import com.rabbitmq.client.Channel;

public interface Producer {
    void produce(Channel channel);
}

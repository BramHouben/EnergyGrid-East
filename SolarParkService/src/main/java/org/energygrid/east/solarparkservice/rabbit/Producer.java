package org.energygrid.east.solarparkservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Producer {

    void produce(Channel channel);
}

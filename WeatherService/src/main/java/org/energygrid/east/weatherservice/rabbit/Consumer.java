package org.energygrid.east.weatherservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer {

    void consume(Channel channel);
}

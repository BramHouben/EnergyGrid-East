package org.energygrid.east.weatherservice.rabbit;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class ChannelHelper {

    private final Channel channel;

    protected ChannelHelper() {
        this.channel = RabbitConfiguration.getInstance().getChannel();
    }

    protected Channel getChannel() {
        return channel;
    }

    protected void closeChannel() {
        try {
            if(channel != null && channel.isOpen()) {
                channel.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}

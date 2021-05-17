package org.energygrid.east.userservice.rabbit;

import org.energygrid.east.userservice.rabbit.Producer.Producer;

public class RabbitProducer extends ChannelHelper {
    public RabbitProducer() {
        super();
    }

    public void produce(Producer producer) {
            producer.produce(getChannel());
            closeChannel();
        }
}
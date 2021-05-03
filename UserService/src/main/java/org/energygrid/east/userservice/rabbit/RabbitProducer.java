package org.energygrid.east.userservice.rabbit;

import org.energygrid.east.userservice.rabbit.Producer.Producer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitProducer extends ChannelHelper {
    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());

    public RabbitProducer() {
        super();
    }

    public void produce(Producer producer) {
            producer.produce(getChannel());
            closeChannel();
        }
}
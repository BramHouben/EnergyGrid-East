package org.energygrid.east.weatherservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitProducer extends ChannelHelper {

    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());

    public RabbitProducer() {
        super();
    }

    public void produce(Producer producer) {
        try {
            producer.produce(getChannel());
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
        } finally {
            closeChannel();
        }
    }
}

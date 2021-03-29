package org.energygrid.east.simulationservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitProducer<T> extends ChannelHelper{

    private static final Logger logger = Logger.getLogger(RabbitProducer.class.getName());

    public RabbitProducer() {

    }

    public T produce(Producer<T> producer) {
        try {
            return producer.produce(getChannel());
        }
        catch (Exception e){
            logger.log(Level.ALL, e.getMessage());
            return null;
        }
        finally {
            closeChannel();
        }
    }

}

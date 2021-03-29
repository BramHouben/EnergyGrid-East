package org.energygrid.east.simulationservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitConsumer<T> extends ChannelHelper{

    private static final Logger logger = Logger.getLogger(RabbitConsumer.class.getName());

    public T consume(Consumer<T> consumer) {
        try {
            return consumer.consume(getChannel());
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

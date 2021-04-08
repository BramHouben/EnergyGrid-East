package org.energygrid.east.simulationsolarservice;

import com.rabbitmq.client.Channel;
import org.energygrid.east.simulationsolarservice.rabbit.RabbitConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RabbitConfigurationTest {

    @Test
    public void testRabbitConnection() {

        RabbitConfiguration rabbitConfiguration = RabbitConfiguration.getInstance();

        Channel channel = rabbitConfiguration.getChannel();

        Assertions.assertNotNull(channel);

    }
}

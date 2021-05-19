package org.energygrid.east.solarparkservice;

import com.rabbitmq.client.Channel;
import org.energygrid.east.solarparkservice.rabbit.RabbitConfiguration;
import org.energygrid.east.solarparkservice.rabbit.mock.ChannelMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@SpringBootTest
class RabbitConfigurationTest {

    @Mock
    private RabbitConfiguration rabbitConfiguration;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testRabbitInstance() {
        Channel channel = new ChannelMock();
        when(rabbitConfiguration.getChannel()).thenReturn(channel);

        assertEquals(channel, rabbitConfiguration.getChannel());
    }

}

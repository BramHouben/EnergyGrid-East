package org.energygrid.east.solarparkservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.energygrid.east.solarparkservice.rabbit.RabbitConfiguration;
import org.energygrid.east.solarparkservice.rabbit.mock.ChannelMock;
import org.energygrid.east.solarparkservice.rabbit.mock.ConnectionMock;
import org.energygrid.east.solarparkservice.service.ISolarParkPower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Mockito.when;

import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitConfigurationTest {

    @Mock
    private RabbitConfiguration rabbitConfiguration;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testRabbitInstance() {
        Channel channel = new ChannelMock();
        when(rabbitConfiguration.getChannel()).thenReturn(channel);

        Assert.assertEquals(channel, rabbitConfiguration.getChannel());
    }

}

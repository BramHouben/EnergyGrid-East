package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.model.dto.SimulationSolarDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SimulationSonarDTOTest {

    @Test
    public void testConstructor() {
        Point point = new Point(123, 456);

        SimulationSolarDTO simulationSolarDTO = new SimulationSolarDTO("testSolar", 10, point);

        Assert.assertEquals("testSolar", simulationSolarDTO.getSolarParkName());
        Assert.assertEquals(10, simulationSolarDTO.getCountSonarPanels());
        Assert.assertEquals(123, simulationSolarDTO.getCoordinates().getX(), 0);
        Assert.assertEquals(456, simulationSolarDTO.getCoordinates().getY(), 0);
    }
}

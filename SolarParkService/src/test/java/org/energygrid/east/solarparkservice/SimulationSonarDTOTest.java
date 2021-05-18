package org.energygrid.east.solarparkservice;

import org.energygrid.east.solarparkservice.model.dto.SimulationSolarDTO;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@SpringBootTest
 class SimulationSonarDTOTest {

    @Test
     void testConstructor() {
        Point point = new Point(123, 456);

        SimulationSolarDTO simulationSolarDTO = new SimulationSolarDTO("testSolar", 10, point);

        assertEquals("testSolar", simulationSolarDTO.getSolarParkName());
        assertEquals(10, simulationSolarDTO.getCountSonarPanels());
        assertEquals(123, simulationSolarDTO.getCoordinates().getX(), 0);
        assertEquals(456, simulationSolarDTO.getCoordinates().getY(), 0);
    }
}

package org.energygrid.east.simulationsolarservice;

import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;
import org.energygrid.east.simulationsolarservice.service.SimulationSolarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class SimulationSolarServiceTest {

    @Test
    void testSimulationEmptyList() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();

        Assertions.assertNull(simulationSolarService.getSimulationById("123"));
    }

    @Test
    void testAddSimulationCorrect() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();
        SimulationSolar simulationSolar = new SimulationSolar("1");

        simulationSolarService.addSimulation(simulationSolar);

        Assertions.assertEquals(simulationSolar, simulationSolarService.getSimulationById("1"));
    }

    @Test
    void testDeleteSimulation() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();
        SimulationSolar simulationSolar = new SimulationSolar("1");

        simulationSolarService.addSimulation(simulationSolar);

        Assertions.assertEquals(simulationSolar, simulationSolarService.getSimulationById("1"));

        simulationSolarService.deleteSimulation("1");

        Assertions.assertNull(simulationSolarService.getSimulationById("1"));
    }

    @Test
    void testSimulation() {
        SimulationSolarService simulationSolarService = new SimulationSolarService();
        List<EnergyRegionSolarParksInput> energyRegionSolarParksInputs = new ArrayList<>();
        Point point = new Point(23, 34);
        energyRegionSolarParksInputs.add(new EnergyRegionSolarParksInput("test", 23, point));
        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = simulationSolarService.simulateEnergyGrid(energyRegionSolarParksInputs);

        Assertions.assertNotEquals(0, energyRegionSolarParksOutput.getKwh().size());
    }
}

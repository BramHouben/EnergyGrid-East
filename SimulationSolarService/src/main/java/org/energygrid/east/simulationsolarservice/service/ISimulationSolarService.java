package org.energygrid.east.simulationsolarservice.service;

import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;

import java.util.List;

public interface ISimulationSolarService {

    SimulationSolar getSimulationById(String id);

    void addSimulation(SimulationSolar simulationSolar);

    void deleteSimulation(String id);

    EnergyRegionSolarParksOutput simulateEnergyGrid(List<EnergyRegionSolarParksInput> energyRegionSolarParksInput);
}

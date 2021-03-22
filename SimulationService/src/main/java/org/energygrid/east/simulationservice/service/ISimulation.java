package org.energygrid.east.simulationservice.service;

import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationservice.model.Simulation;

import java.util.List;

public interface ISimulation {

    Simulation getSimulationById(String id);

    void addSimulation(Simulation simulation);

    void deleteSimulation(String id);

    EnergyRegionSolarParksOutput simulationEnergyGrid(List<EnergyRegionSolarParksInput> energyRegionSolarParksInput);
}

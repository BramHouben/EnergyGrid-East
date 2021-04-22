package org.energygrid.east.simulationsolarservice.service;

import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationsolarservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationsolarservice.model.SimulationSolar;

import java.util.List;

public interface ISimulationSolarService {

    /**
     * @param id from simulation
     * @return simulation from id
     */
    SimulationSolar getSimulationById(String id);

    /**
     * @param simulationSolar model that is added in db
     */
    void addSimulation(SimulationSolar simulationSolar);

    /**
     * @param id from simulation
     *
     */
    void deleteSimulation(String id);

    /**
     * @param energyRegionSolarParksInput list of solar parks
     * @return output from solar parks from region
     */
    EnergyRegionSolarParksOutput simulateEnergyGrid(List<EnergyRegionSolarParksInput> energyRegionSolarParksInput);
}

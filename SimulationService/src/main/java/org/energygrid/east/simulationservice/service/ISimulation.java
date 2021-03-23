package org.energygrid.east.simulationservice.service;

import org.energygrid.east.simulationservice.model.Simulation;

public interface ISimulation {

    Simulation getSimulationById(String id);

    void addSimulation(Simulation simulation);

    void deleteSimulation(String id);
}

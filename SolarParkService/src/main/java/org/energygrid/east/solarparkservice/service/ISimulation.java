package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.model.Simulation;

import java.util.List;

public interface ISimulation {

    Simulation getSimulationById(String id);

    void addSimulation(Simulation simulation);
}

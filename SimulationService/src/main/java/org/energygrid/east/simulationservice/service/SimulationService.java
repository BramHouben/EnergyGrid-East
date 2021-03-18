package org.energygrid.east.simulationservice.service;

import org.energygrid.east.simulationservice.model.Simulation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService implements ISimulation{

    private final List<Simulation> simulations;

    public SimulationService() {
        simulations = new ArrayList<>();
    }

    @Override
    public Simulation getSimulationById(String id) {
        return simulations.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void addSimulation(Simulation simulation) {
        simulations.add(simulation);
    }

    @Override
    public void deleteSimulation(String id) {
        Simulation simulation = simulations.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        simulations.remove(simulation);
    }
}

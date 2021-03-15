package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.model.Simulation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService implements ISimulation{

    private List<Simulation> simulations;

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
}

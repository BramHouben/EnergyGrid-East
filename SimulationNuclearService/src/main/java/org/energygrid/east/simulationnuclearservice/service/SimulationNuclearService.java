package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.AddNuclearPowerplantDTO;
import org.energygrid.east.simulationnuclearservice.repository.SimulationNuclearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimulationNuclearService implements ISimulationNuclearService {

    @Autowired
    private SimulationNuclearRepository simulationNuclearRepository;

    @Override
    public Simulation addSimulation(AddNuclearPowerplantDTO addNuclearPowerplantDTO) {
        Simulation simulation = new Simulation(UUID.randomUUID(), addNuclearPowerplantDTO.getName(), addNuclearPowerplantDTO.getReactorGeneration(), addNuclearPowerplantDTO.getCoordinates(), addNuclearPowerplantDTO.getMaxPower(), addNuclearPowerplantDTO.getBuildYear());
        simulationNuclearRepository.save(simulation);
        return simulation;
    }

    @Override
    public boolean removeSimulation(UUID id) {
        return simulationNuclearRepository.deleteSimulationBySimulationId(id);
    }

    @Override
    public List<Simulation> getSimulations() {
        return simulationNuclearRepository.findAll();
    }

}

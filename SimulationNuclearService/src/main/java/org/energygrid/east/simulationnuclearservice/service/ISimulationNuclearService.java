package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.AddNuclearPowerplantDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ISimulationNuclearService {

    Simulation addSimulation(AddNuclearPowerplantDTO addNuclearPowerplantDTO);

    boolean removeSimulation(UUID id);

    List<Simulation> getSimulations();
}

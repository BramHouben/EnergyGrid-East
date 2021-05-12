package org.energygrid.east.simulationnuclearservice.repository;

import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SimulationNuclearRepository extends MongoRepository<Simulation, String> {
    Simulation getSimulationBySimulationId(UUID simulationId);
    boolean deleteSimulationBySimulationId(UUID simulationId);
}

package org.energygrid.east.simulationsolarservice.repository;

import org.energygrid.east.simulationsolarservice.model.results.SimulationExpectationResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SimulationSolarRepository extends MongoRepository<SimulationExpectationResult, String> {
}

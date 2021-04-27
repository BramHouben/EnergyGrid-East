package org.energygrid.east.simulationwindservice.repository;

import org.energygrid.east.simulationwindservice.model.results.SimulationExpectationResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface SimulationWindRepository extends MongoRepository<SimulationExpectationResult, String> {

}

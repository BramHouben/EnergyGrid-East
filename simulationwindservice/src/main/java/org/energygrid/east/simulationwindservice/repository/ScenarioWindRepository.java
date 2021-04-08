package org.energygrid.east.simulationwindservice.repository;

import org.energygrid.east.simulationwindservice.model.results.ScenarioExpectationResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioWindRepository extends MongoRepository<ScenarioExpectationResult, String> {
}

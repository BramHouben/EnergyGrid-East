package org.energygrid.east.simulationnuclearservice.repository;

import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioNuclearRepository extends MongoRepository<Scenario, String> {
}

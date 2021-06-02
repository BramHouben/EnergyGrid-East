package org.energygrid.east.simulationnuclearservice.repository;

import org.energygrid.east.simulationnuclearservice.model.results.ScenarioExpectationResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioNuclearRepository extends MongoRepository<ScenarioExpectationResult, String> {
    List<ScenarioExpectationResult> findTop3ByOrderByCreatedAtDesc();
}

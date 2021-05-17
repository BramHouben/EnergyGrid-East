package org.energygrid.east.simulationsolarservice.repository;

import org.energygrid.east.simulationsolarservice.model.results.ScenarioExpectationResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioSolarRepository extends MongoRepository<ScenarioExpectationResult, String> {
    List<ScenarioExpectationResult> findTop3ByOrderByCreatedAtDesc();
    int countAllByCreatedAtBetween(String startDate, String endDate);
    List<ScenarioExpectationResult> findAllByCreatedAtBetween(String startDate, String endDate);
}

package org.energygrid.east.simulationsolarservice.repository;

import org.energygrid.east.simulationsolarservice.model.SolarParkProduction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SolarParkProductionRepository extends MongoRepository<SolarParkProduction, String> {
    SolarParkProduction findSolarParkProductionsBySolarPark_SolarParkId(int id);
    List<SolarParkProduction> findAllByOrderByTodayProductionDesc();
}

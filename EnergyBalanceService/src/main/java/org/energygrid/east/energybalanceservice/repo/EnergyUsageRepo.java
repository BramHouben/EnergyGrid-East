package org.energygrid.east.energybalanceservice.repo;

import org.energygrid.east.energybalanceservice.model.EnergyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyUsageRepo extends MongoRepository<EnergyUsage, String> {

    EnergyUsage findFirstByOrderByDayDesc();

}

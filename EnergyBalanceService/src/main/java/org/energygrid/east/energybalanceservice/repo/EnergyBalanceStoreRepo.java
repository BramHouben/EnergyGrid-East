package org.energygrid.east.energybalanceservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyBalanceStoreRepo extends MongoRepository<org.energygrid.east.energybalanceservice.model.EnergyBalanceStore, String> {
}

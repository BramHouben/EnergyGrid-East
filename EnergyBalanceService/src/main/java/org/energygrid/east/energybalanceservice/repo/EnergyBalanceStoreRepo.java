package org.energygrid.east.energybalanceservice.repo;

import org.energygrid.east.energybalanceservice.model.EnergyBalanceStore;
import org.energygrid.east.energybalanceservice.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyBalanceStoreRepo extends MongoRepository<EnergyBalanceStore, String> {

        EnergyBalanceStore findFirstByType(Type  type);
}

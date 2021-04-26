package org.energygrid.east.energybalanceservice.repo;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EnergyBalanceRepo  extends MongoRepository<EnergyBalance, String> {

    EnergyBalance findFirstByOrderByTimeDesc();
//    EnergyBalance getFirstByTime(LocalDateTime localDateTime);
}

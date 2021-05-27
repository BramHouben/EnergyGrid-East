package com.enerygrid.east.energyusageservice.repository;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyUsageRepository extends MongoRepository<EnergyUsage, String> {
    List<EnergyUsage> findUsageByUserIdAndDay(String userId, String day);
    EnergyUsage findFirstById();
}

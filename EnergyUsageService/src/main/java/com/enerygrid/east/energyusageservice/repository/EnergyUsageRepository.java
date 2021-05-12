package com.enerygrid.east.energyusageservice.repository;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EnergyUsageRepository extends MongoRepository<EnergyUsage, String> {
    List<EnergyUsage> findUsageByUserId(String userId, String day);
}

package org.energygrid.east.solarparkservice.repo;

import org.energygrid.east.solarparkservice.model.SolarPark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ISolarParkRepo extends MongoRepository<SolarPark, String> {

    SolarPark getSolarParkBySolarParkName(String name);

    SolarPark getSolarParkBySolarParkId(UUID uuid);

    void removeBySolarParkName(String name);

    boolean existsBySolarParkId(UUID uuid);

    boolean existsBySolarParkName(String name);
}

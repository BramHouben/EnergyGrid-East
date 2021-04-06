package org.energygrid.east.regionservice.repo;


import org.energygrid.east.regionservice.model.House;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegionRepo extends MongoRepository<House, String> {

    List<House> getAllByRegion(String provinceName);
    List<House> getAllByCity(String city);
    List<House> getAllByStreet(String street);
}

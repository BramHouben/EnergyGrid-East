package org.energygrid.east.regionservice.repo;


import org.energygrid.east.regionservice.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegionRepo extends MongoRepository<House, String> {

    List<House> getAllByRegion(String provinceName);
    List<House> getAllByCity(String city);
    List<House> getAllByStreetAndCity(String street, String city);
    Page<House> getAllByStreetAndCityOrderByNumberAsc(String street,String city,  Pageable pageable);
    List<House> getHouseByRegion (String region);
    List<House> findDistinctByRegionOrderByCity(String region);

    int countAllByCity(String city);

}

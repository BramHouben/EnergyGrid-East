package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService implements IRegionService {

    @Autowired
    private IRegionRepo regionRepo;

    @Override
    public List<House> getAllHousesProvince(String regionName) {
        return regionRepo.getAllByRegion(regionName);
    }

    @Override
    public List<House> getAllHousesCity(String cityName) {
        return regionRepo.getAllByCity(cityName);
    }

    @Override
    public List<House> getAllHousesStreet(String streetName) {
        return regionRepo.getAllByStreet(streetName);
    }
}
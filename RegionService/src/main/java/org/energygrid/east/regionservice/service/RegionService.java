package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;
import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService implements IRegionService {

    @Autowired
    private IRegionRepo regionRepo;

    @Override
    public List<House> getAllHousesProvince(String regionName, long page) {
        page *= 10;

        return regionRepo.getAllByRegion(regionName).stream().skip(page).limit(10).collect(Collectors.toList());
    }

    @Override
    public List<House> getAllHousesCity(String cityName, long page) {
        page *= 10;
        return regionRepo.getAllByCity(cityName).stream().skip(page).limit(10).collect(Collectors.toList());
    }

    @Override
    public StreetRequest getAllHousesStreet(String streetName, long page) {
        page *= 10;
        StreetRequest streetRequest = new StreetRequest();
        streetRequest.setTotalStreet(regionRepo.getAllByStreet(streetName).size());
        streetRequest.setHouses(regionRepo.getAllByStreet(streetName).stream().skip(page).limit(10).collect(Collectors.toList()));

        return streetRequest;
    }
}
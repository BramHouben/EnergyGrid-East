package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;
import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RegionService implements IRegionService {

    private static final java.util.logging.Logger logger = Logger.getLogger(RegionService.class.getName());

    @Autowired
    private IRegionRepo regionRepo;

    @Override
    public List<House> getAllHousesProvince(String regionName, long page) {
        logger.log(Level.INFO, "getAllHousesProvince method called");
        page *= 10;

        return regionRepo.getAllByRegion(regionName).stream().skip(page).limit(10).collect(Collectors.toList());
    }

    @Override
    public List<House> getAllHousesCity(String cityName, long page) {
        logger.log(Level.INFO, "getAllHousesCity method called");
        page *= 10;
        return regionRepo.getAllByCity(cityName).stream().skip(page).limit(10).collect(Collectors.toList());
    }

    @Override
    public StreetRequest getAllHousesStreet(String streetName, long page) {
        logger.log(Level.INFO, "getAllHousesStreet method called" + page);
        if (page > 0) {
            page--;
        }

        StreetRequest streetRequest = new StreetRequest();
        streetRequest.setTotalStreet(regionRepo.getAllByStreet(streetName).size());

        Pageable pageable = PageRequest.of((int) page, 10);
        List<House> houses = regionRepo.getAllByStreetOrderByNumberAsc(streetName, pageable).toList();
        streetRequest.setHouses(houses);

        logger.log(Level.INFO, "street request made by user");
        return streetRequest;
    }
}
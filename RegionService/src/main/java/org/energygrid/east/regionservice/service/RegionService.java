package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.CityInfoRequest;
import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;
import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RegionService implements IRegionService {

    private static final java.util.logging.Logger logger = Logger.getLogger(RegionService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IRegionRepo regionRepo;

    private List<String> cities;

    private List<String> regions;

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
    public StreetRequest getAllHousesStreet(String streetName, String city) {
        logger.log(Level.INFO, "getAllHousesStreet method called");

        var streetRequest = new StreetRequest();
        streetRequest.setTotalStreet(regionRepo.getAllByStreetAndCity(streetName, city).size());

        List<House> houses = regionRepo.getAllByStreetAndCityOrderByNumberAsc(streetName, city);
        streetRequest.setHouses(houses);

        logger.log(Level.INFO, "street request made by user");
        return streetRequest;
    }

    @Override
    public List<String> getAllCitiesRegion(String region) {
        if (!regions.contains(region)) {
            throw new IllegalArgumentException();
        }
        logger.log(Level.INFO, () -> "getAllCitiesRegion method called" + region);

        var criteria = new Criteria("Region").is(region);
        var query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.findDistinct(query, "city", House.class, String.class).stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CityInfoRequest getInfoCity(String city) {

        if (!cities.contains(city)) {
            throw new IllegalArgumentException();
        }


        logger.log(Level.INFO, () -> "CityInfoRequest method called" + city);

        var criteria = new Criteria("city").is(city);
        var query = new Query();
        query.addCriteria(criteria);

        int totalCountHouses = regionRepo.countAllByCity(city);
        //  estimate
        int totalWithSonarPanels = (int) Math.round(totalCountHouses * 0.25);

        List<String> streets = mongoTemplate.findDistinct(query, "street", House.class, String.class).stream().sorted(String::compareToIgnoreCase).collect(Collectors.toUnmodifiableList());

        return new CityInfoRequest(totalCountHouses, totalWithSonarPanels, 2832, streets);

    }

    @Autowired
    private void fillListWithCities() {
        cities = mongoTemplate.findDistinct("city", House.class, String.class).stream().collect(Collectors.toUnmodifiableList());
    }

    @Autowired
    private void fillListWithRegions() {

        regions = mongoTemplate.findDistinct("region", House.class, String.class).stream().collect(Collectors.toUnmodifiableList());
    }

}
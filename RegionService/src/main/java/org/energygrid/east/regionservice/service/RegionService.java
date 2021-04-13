package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.CityInfoRequest;
import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;
import org.energygrid.east.regionservice.repo.IRegionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public StreetRequest getAllHousesStreet(String streetName, String city, long page) {
        logger.log(Level.INFO, "getAllHousesStreet method called" + page);
        if (page > 0) {
            page--;
        }

        StreetRequest streetRequest = new StreetRequest();
        streetRequest.setTotalStreet(regionRepo.getAllByStreetAndCity(streetName, city).size());

        Pageable pageable = PageRequest.of((int) page, 10);
        List<House> houses = regionRepo.getAllByStreetAndCityOrderByNumberAsc(streetName, city, pageable).toList();
        streetRequest.setHouses(houses);

        logger.log(Level.INFO, "street request made by user");
        return streetRequest;
    }

    @Override
    public List<String> getAllCitiesRegion(String region) {

        logger.log(Level.INFO, "getAllCitiesRegion method called" + region);
        if (!region.equals("Flevoland") && !region.equals("Gelderland") && !region.equals("Overijssel")) {
            throw new IllegalArgumentException();
        }
        Criteria criteria = new Criteria("Region").is(region);
        Query query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.findDistinct(query, "city", House.class, String.class).stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CityInfoRequest getInfoCity(String city) {
        logger.log(Level.INFO, "CityInfoRequest method called" + city);

        Criteria criteria = new Criteria("city").is(city);
        Query query = new Query();
        query.addCriteria(criteria);

        int totalCountHouses = regionRepo.countAllByCity(city);
        //  estimate
        int totalWithSonarPanels = (int) Math.round(totalCountHouses * 0.25);

        List<String> streets = mongoTemplate.findDistinct(query, "street", House.class, String.class).stream().sorted(String::compareToIgnoreCase).collect(Collectors.toUnmodifiableList());

        return new CityInfoRequest(totalCountHouses, totalWithSonarPanels, 2832, streets);

    }

}
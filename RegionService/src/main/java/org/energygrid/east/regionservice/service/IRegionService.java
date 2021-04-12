package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.House;
import org.energygrid.east.regionservice.model.StreetRequest;

import java.util.List;

public interface IRegionService {


    /**
     * @param regionName is the name of the region
     * @param page page of request
     * @return returns all info from the region
     */
    List<House> getAllHousesProvince(String regionName, long page);


    /**
     * @param cityName name of the city
     * @param page page of request
     * @return all houses in a city
     */
    List<House> getAllHousesCity(String cityName, long page);


    /**
     * @param streetName name of the street
     * @param page page of request
     * @return all houses in a street
     */
    StreetRequest getAllHousesStreet(String streetName, long page);
}

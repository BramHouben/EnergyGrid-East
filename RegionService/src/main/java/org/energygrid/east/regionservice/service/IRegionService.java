package org.energygrid.east.regionservice.service;

import org.energygrid.east.regionservice.model.House;

import java.util.List;

public interface IRegionService {


    /**
     * @param regionName is the name of the region
     * @return returns all info from the region
     */
    List<House> getAllHousesProvince(String regionName,int page);


    /**
     * @param cityName name of the city
     * @return all houses in a city
     */
    List<House> getAllHousesCity(String cityName,int page);


    /**
     * @param streetName name of the street
     * @return all houses in a street
     */
    List<House> getAllHousesStreet(String streetName,int page);
}

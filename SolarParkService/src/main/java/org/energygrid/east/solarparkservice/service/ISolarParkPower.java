package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.model.SolarPark;

import java.util.UUID;

public interface ISolarParkPower {

    /**
     * a solar park based on an id
     *
     * @param name
     * @return the solar park with the id
     */
    SolarPark getSolarParkByName(String name);


    /**
     * Checks if solarpark exist by searching the id
     *
     * @param name
     * @return true if solarpark exist false if doesnt
     */
    boolean doesNameExist(String name);


    /**
     * @param solarPark
     */
    void addSolarPark(SolarPark solarPark );

    /**
     * Remove solarPark based on name
     *
     * @param name for deleting
     */
    void removeSolarPark(String name);

    /**
     * Update solarpark based on id
     *
     * @param id
     * @param name
     * @param solarpanels
     */
    void updateSolarPark(UUID id, String name, int solarpanels);

}

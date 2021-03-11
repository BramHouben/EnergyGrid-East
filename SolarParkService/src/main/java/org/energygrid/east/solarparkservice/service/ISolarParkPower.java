package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.model.SolarPark;

public interface ISolarParkPower {


    /**
     * a solar park based on an id
     * @return the solar park with the id
     * @param id
     */
    SolarPark getSolarParkById(int id);


    /**
     * Checks if solarpark exist by searching the id
     * @param id
     * @return true if solarpark exist false if doesnt
     */
    boolean doesIdExist(int id);


    /**
     * Add solarpark to the region
     * @param totalSonarPanels total of sonarpanels in the park
     * @param name name of the solarpark
     */
    void addSolarPark(int totalSonarPanels, String name);

    /**
     * Remove solarPark based on name
     * @param name for deleting
     */
    void removeSolarPark(String name);

    /**
     * Update solarpark based on id
     * @param id
     * @param name
     * @param solarpanels
     */
    void updateSolarPark(int id, String name, int solarpanels);

}

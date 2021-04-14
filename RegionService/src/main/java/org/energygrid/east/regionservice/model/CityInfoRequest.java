package org.energygrid.east.regionservice.model;

import java.util.List;

public class CityInfoRequest {

    private final int countHouses;
    private final int countSolarPanelHouses;
    private final int averageUsageEnergyRegion;
    private final List<String> streetsCity;

    public CityInfoRequest(int countHouses, int countSolarPanelHouses, int averageUsageEnergyRegion, List<String> streetsCity) {
        this.countHouses = countHouses;
        this.countSolarPanelHouses = countSolarPanelHouses;
        this.averageUsageEnergyRegion = averageUsageEnergyRegion;
        this.streetsCity = streetsCity;
    }

    public int getCountHouses() {
        return countHouses;
    }

    public int getCountSolarPanelHouses() {
        return countSolarPanelHouses;
    }

    public int getAverageUsageEnergyRegion() {
        return averageUsageEnergyRegion;
    }

    public List<String> getStreetsCity() {
        return streetsCity;
    }

}
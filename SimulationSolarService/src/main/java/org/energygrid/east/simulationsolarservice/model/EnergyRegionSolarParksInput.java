package org.energygrid.east.simulationsolarservice.model;

import org.springframework.data.geo.Point;

public class EnergyRegionSolarParksInput {

    private String solarParkName;
    private int totalCountSolarPanels;
    private Point placeSolarPark;

    public EnergyRegionSolarParksInput(String solarParkName, int totalCountSolarPanels, Point placeSolarPark) {
        this.solarParkName = solarParkName;
        this.totalCountSolarPanels = totalCountSolarPanels;
        this.placeSolarPark = placeSolarPark;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public void setSolarParkName(String solarParkName) {
        this.solarParkName = solarParkName;
    }

    public int getTotalCountSolarPanels() {
        return totalCountSolarPanels;
    }

    public void setTotalCountSolarPanels(int totalCountSolarPanels) {
        this.totalCountSolarPanels = totalCountSolarPanels;
    }

    public Point getPlaceSolarPark() {
        return placeSolarPark;
    }

    public void setPlaceSolarPark(Point placeSolarPark) {
        this.placeSolarPark = placeSolarPark;
    }

    @Override
    public String toString() {
        return "EnergyRegionSolarParksInput{" +
                "solarParkName='" + solarParkName + '\'' +
                ", totalCountSolarPanels=" + totalCountSolarPanels +
                ", placeSolarPark=" + placeSolarPark +
                '}';
    }
}

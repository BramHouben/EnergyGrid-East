package org.energygrid.east.simulationservice.model;

import org.springframework.data.geo.Point;

import java.io.Serializable;

public class EnergyRegionSolarParksInput implements Serializable {

    private  String solarParkName;
    private int totalCountSolarPanels;
    private  Point placeSolarPark;

    public EnergyRegionSolarParksInput(String solarParkName, int totalCountSolarPanels, Point placeSolarPark) {
        this.solarParkName = solarParkName;
        this.totalCountSolarPanels = totalCountSolarPanels;
        this.placeSolarPark = placeSolarPark;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public int getTotalCountSolarPanels() {
        return totalCountSolarPanels;
    }

    public Point getPlaceSolarPark() {
        return placeSolarPark;
    }

    public void setPlaceSolarPark(Point placeSolarPark) {
        this.placeSolarPark = placeSolarPark;
    }

    public void setSolarParkName(String solarParkName) {
        this.solarParkName = solarParkName;
    }

    public void setTotalCountSolarPanels(int totalCountSolarPanels) {
        this.totalCountSolarPanels = totalCountSolarPanels;
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

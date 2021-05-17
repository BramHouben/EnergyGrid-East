package org.energygrid.east.simulationsolarservice.model;

import org.springframework.data.geo.Point;

import java.util.List;

public class SolarPark {

    private int solarParkId;
    private String description;
    private Point coordinates;
    private List<SolarUnit> solarUnits;
    private int yearOfRealised;

    public SolarPark() {}

    public SolarPark(int solarParkId, String description, Point coordinates, List<SolarUnit> solarUnits, int yearOfRealised) {
        this.solarParkId = solarParkId;
        this.description = description;
        this.coordinates = coordinates;
        this.solarUnits = solarUnits;
        this.yearOfRealised = yearOfRealised;
    }

    public int getSolarParkId() {
        return solarParkId;
    }

    public void setSolarParkId(int solarParkId) {
        this.solarParkId = solarParkId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public List<SolarUnit> getSolarUnits() {
        return solarUnits;
    }

    public void setSolarUnits(List<SolarUnit> solarUnits) {
        this.solarUnits = solarUnits;
    }

    public int getYearOfRealised() {
        return yearOfRealised;
    }

    public void setYearOfRealised(int yearOfRealised) {
        this.yearOfRealised = yearOfRealised;
    }
}

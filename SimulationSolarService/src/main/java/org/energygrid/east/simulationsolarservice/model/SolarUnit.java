package org.energygrid.east.simulationsolarservice.model;

import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.springframework.data.geo.Point;

public class SolarUnit {

    private int solarUnitId;
    private String description;
    private Point coordinates;
    private SolarPanelType solarPanelType;
    private int numberOfPanels;

    public SolarUnit() {}

    public SolarUnit(Point coordinates, SolarPanelType solarPanelType, int numberOfPanels) {
        this.coordinates = coordinates;
        this.solarPanelType = solarPanelType;
        this.numberOfPanels = numberOfPanels;
    }

    public SolarUnit(int solarUnitId, String description, Point coordinates, SolarPanelType solarPanelType, int numberOfPanels) {
        this.solarUnitId = solarUnitId;
        this.description = description;
        this.coordinates = coordinates;
        this.solarPanelType = solarPanelType;
        this.numberOfPanels = numberOfPanels;
    }

    public int getSolarUnitId() {
        return solarUnitId;
    }

    public void setSolarUnitId(int solarUnitId) {
        this.solarUnitId = solarUnitId;
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

    public SolarPanelType getSolarPanelType() {
        return solarPanelType;
    }

    public void setSolarPanelType(SolarPanelType solarPanelType) {
        this.solarPanelType = solarPanelType;
    }

    public int getNumberOfPanels() {
        return numberOfPanels;
    }

    public void setNumberOfPanels(int numberOfPanels) {
        this.numberOfPanels = numberOfPanels;
    }
}

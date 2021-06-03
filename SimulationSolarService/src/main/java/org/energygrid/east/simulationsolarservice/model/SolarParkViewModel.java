package org.energygrid.east.simulationsolarservice.model;

import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.springframework.data.geo.Point;

public class SolarParkViewModel {

    private int solarParkId;
    private String description;
    private Point coordinates;
    private int amountOfUnits;
    private SolarPanelType solarPanelType;

    public SolarParkViewModel() {
    }

    public SolarParkViewModel(int solarParkId, String description, Point coordinates, int amountOfUnits, SolarPanelType solarPanelType) {
        this.solarParkId = solarParkId;
        this.description = description;
        this.coordinates = coordinates;
        this.amountOfUnits = amountOfUnits;
        this.solarPanelType = solarPanelType;
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

    public int getAmountOfUnits() {
        return amountOfUnits;
    }

    public void setAmountOfUnits(int amountOfUnits) {
        this.amountOfUnits = amountOfUnits;
    }

    public SolarPanelType getSolarPanelType() {
        return solarPanelType;
    }

    public void setSolarPanelType(SolarPanelType solarPanelType) {
        this.solarPanelType = solarPanelType;
    }
}

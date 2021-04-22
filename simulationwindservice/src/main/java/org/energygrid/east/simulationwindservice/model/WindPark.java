package org.energygrid.east.simulationwindservice.model;

import org.springframework.data.geo.Point;

import java.util.List;

public class WindPark {

    private String windParkId;
    private String description;
    private Point coordinates;
    private List<WindTurbine> windTurbines;

    public WindPark() {}

    public WindPark(String windParkId, String description, Point coordinates, List<WindTurbine> windTurbines) {
        this.windParkId = windParkId;
        this.description = description;
        this.coordinates = coordinates;
        this.windTurbines = windTurbines;
    }

    public String getWindParkId() {
        return windParkId;
    }

    public void setWindParkId(String windParkId) {
        this.windParkId = windParkId;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) { this.coordinates = coordinates; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WindTurbine> getWindTurbines() {
        return windTurbines;
    }

    public void setWindTurbines(List<WindTurbine> windTurbines) {
        this.windTurbines = windTurbines;
    }
}

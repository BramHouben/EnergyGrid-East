package org.energygrid.east.simulationwindservice.model;

import org.springframework.data.geo.Point;

public class WindTurbine {

    private int turbineId;
    private String description;
    private Point coordinates;
    private Double type;

    public WindTurbine() {}

    public WindTurbine(int turbineId, String description, Point coordinates, Double type) {
        this.turbineId = turbineId;
        this.description = description;
        this.coordinates = coordinates;
        this.type = type;
    }

    public int getTurbineId() {
        return turbineId;
    }

    public void setTurbineId(int turbineId) {
        this.turbineId = turbineId;
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

    public Double getType() { return type; }

    public void setType(Double type) { this.type = type; }
}

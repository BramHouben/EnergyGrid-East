package org.energygrid.east.simulationwindservice.model;

import org.springframework.data.geo.Point;

import java.util.List;

public class SimulationWindPark {

    private String id;
    private String description;
    private String city;
    private List<WindTurbine> turbines;
    private double kwhTotal;
    private Point coordinates;

    public SimulationWindPark() {
    }

    public SimulationWindPark(String id, String description, String city, List<WindTurbine> turbines, double kwhTotal, Point coordinates) {
        this.id = id;
        this.description = description;
        this.city = city;
        this.turbines = turbines;
        this.kwhTotal = kwhTotal;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<WindTurbine> getTurbines() {
        return turbines;
    }

    public void setTurbines(List<WindTurbine> turbines) {
        this.turbines = turbines;
    }

    public double getKwhTotal() {
        return kwhTotal;
    }

    public void setKwhTotal(double kwhTotal) {
        this.kwhTotal = kwhTotal;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}

package org.energygrid.east.simulationnuclearservice.model.dto;

import java.awt.*;

public class AddNuclearPowerplantDTO {
    private final String name;
    private final int reactorGeneration;
    private final Point coordinates;
    private final int maxPower;
    private final int buildYear;

    public AddNuclearPowerplantDTO(String name, int reactorGeneration, Point coordinates, int maxPower, int buildYear) {
        this.name = name;
        this.reactorGeneration = reactorGeneration;
        this.coordinates = coordinates;
        this.maxPower = maxPower;
        this.buildYear = buildYear;
    }

    public String getName() {
        return name;
    }

    public int getReactorGeneration() {
        return reactorGeneration;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getBuildYear() {
        return buildYear;
    }
}

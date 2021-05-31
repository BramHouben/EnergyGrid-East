package org.energygrid.east.simulationnuclearservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.util.UUID;

@Document(collection = "simulation")
public class Simulation {

    @Id
    private UUID simulationId;

    @Indexed(unique = true)
    private final String name;

    private final int reactorGeneration;

    private final Point coordinates;

    private final int maxPower;

    private final int buildYear;

    public Simulation(UUID simulationId, String name, int reactorGeneration, Point coordinates, int maxPower, int buildYear) {
        this.simulationId = simulationId;
        this.name = name;
        this.reactorGeneration = reactorGeneration;
        this.coordinates = coordinates;
        this.maxPower = maxPower;
        this.buildYear = buildYear;
    }

    public UUID getSimulationId() {
        return simulationId;
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

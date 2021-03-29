package org.energygrid.east.simulationservice.model;

import java.util.UUID;

public class Simulation {

    private final String id;

    public Simulation() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}

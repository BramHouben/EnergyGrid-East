package org.energygrid.east.simulationsolarservice.model;

import java.util.UUID;

public class SimulationSolar {

    private final String id;

    public SimulationSolar() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}

package org.energygrid.east.solarparkservice.model;

import java.util.TimerTask;

public class SimulationTimer extends TimerTask {

    private final Simulation simulation;

    public SimulationTimer(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void run() {
        this.simulation.setTotalKwh();
    }
}

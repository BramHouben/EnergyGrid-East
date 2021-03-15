package org.energygrid.east.solarparkservice.model;

import org.energygrid.east.solarparkservice.timer.SimulationTimer;

import java.util.Timer;
import java.util.UUID;

public class Simulation {

    private String id;
    private double totalKwh;
    private SolarPark solarPark;
    private Timer timer;

    public Simulation(SolarPark solarPark, Timer timer) {
        this.id = UUID.randomUUID().toString();
        this.solarPark = solarPark;
        this.timer = timer;
    }

    public String getId() {
        return id;
    }

    public SolarPark getSolarPark() {
        return solarPark;
    }

    public void setTotalKwh() {
        this.totalKwh = this.totalKwh + (this.solarPark.getCountSonarPanels() * 0.225);
    }

    public double getTotalKwh() {
        return totalKwh;
    }

    public void stopTimer() {
        this.timer.cancel();
    }
}

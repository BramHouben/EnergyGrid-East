package org.energygrid.east.solarparkservice.model;

import java.util.Timer;
import java.util.UUID;

public class Simulation {

    private final String id;
    private final SolarPark solarPark;
    private final Timer timer;
    private double totalKwh;

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

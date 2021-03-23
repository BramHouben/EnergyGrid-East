package org.energygrid.east.simulationservice.model;

import java.time.LocalDateTime;

public class Kwh {
    private double kilowatt;
    private LocalDateTime time;

    public Kwh(double kilowatt, LocalDateTime time) {
        this.kilowatt = kilowatt;
        this.time = time;
    }

    public double getKilowatt() {
        return kilowatt;
    }

    public void setKilowatt(double kilowatt) {
        this.kilowatt = kilowatt;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}

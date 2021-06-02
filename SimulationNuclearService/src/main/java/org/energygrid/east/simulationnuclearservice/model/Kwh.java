package org.energygrid.east.simulationnuclearservice.model;

import java.time.LocalDateTime;

public class Kwh {
    private final double kilowatt;
    private final LocalDateTime time;

    public Kwh(double kilowatt, LocalDateTime time) {
        this.kilowatt = kilowatt;
        this.time = time;
    }

    public double getKilowatt() {
        return kilowatt;
    }

    public LocalDateTime getTime() {
        return time;
    }
}

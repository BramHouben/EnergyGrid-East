package org.energygrid.east.simulationnuclearservice.model;

import java.time.LocalDateTime;

public class ProductionExpectation {

    private double kw;
    private LocalDateTime localDateTime;

    public ProductionExpectation() {
    }

    public ProductionExpectation(double kw, LocalDateTime localDateTime) {
        this.kw = kw;
        this.localDateTime = localDateTime;
    }

    public double getKw() {
        return kw;
    }

    public void setKw(double kw) {
        this.kw = kw;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

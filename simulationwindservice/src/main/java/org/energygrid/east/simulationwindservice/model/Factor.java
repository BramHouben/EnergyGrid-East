package org.energygrid.east.simulationwindservice.model;

public class Factor {

    private String name;
    private double factor;

    public Factor() {
    }

    public Factor(String name, double factor) {
        this.name = name;
        this.factor = factor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }
}

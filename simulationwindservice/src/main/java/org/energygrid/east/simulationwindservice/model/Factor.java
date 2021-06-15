package org.energygrid.east.simulationwindservice.model;

public class Factor {

    private String name;
    private double factorValue;

    public Factor() {
    }

    public Factor(String name, double factor) {
        this.name = name;
        this.factorValue = factor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFactorValue() {
        return factorValue;
    }

    public void setFactor(double factor) {
        this.factorValue = factor;
    }
}

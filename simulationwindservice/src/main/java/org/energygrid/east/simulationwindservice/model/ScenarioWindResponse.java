package org.energygrid.east.simulationwindservice.model;

public class ScenarioWindResponse {

    public double kilowatt;
    public int count;

    public ScenarioWindResponse() {

    }

    public ScenarioWindResponse(double kilowatt, int count) {
        this.kilowatt = kilowatt;
        this.count = count;
    }

    public double getKilowatt() {
        return kilowatt;
    }

    public void setKilowatt(double kilowatt) {
        this.kilowatt = kilowatt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

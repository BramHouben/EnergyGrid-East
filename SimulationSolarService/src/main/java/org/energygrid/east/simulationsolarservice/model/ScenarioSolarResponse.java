package org.energygrid.east.simulationsolarservice.model;

public class ScenarioSolarResponse {

    public double kilowatt;
    public int count;

    public ScenarioSolarResponse() {

    }

    public ScenarioSolarResponse(double kilowatt, int count) {
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

package org.energygrid.east.simulationservice.model;

import java.util.ArrayList;
import java.util.List;

public class EnergyRegionSolarParksOutput {

    List<Double> Kwh = new ArrayList<>();

    public List<Double> getKwh() {
        return Kwh;
    }

    public void addKwh(Double kwh) {
        this.Kwh.add(kwh);
    }

    public EnergyRegionSolarParksOutput() {

    }
}

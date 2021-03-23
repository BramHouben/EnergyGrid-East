package org.energygrid.east.simulationservice.model;

import java.util.ArrayList;
import java.util.List;

public class EnergyRegionSolarParksOutput {

    List<Kwh> Kwh = new ArrayList<>();

    public List<Kwh> getKwh() {
        return Kwh;
    }

    public void addKwh(Kwh kwh) {
        this.Kwh.add(kwh);
    }

    public EnergyRegionSolarParksOutput() {

    }
}

package org.energygrid.east.simulationsolarservice.model;

import java.util.ArrayList;
import java.util.List;

public class EnergyRegionSolarParksOutput {

    List<Kwh> kwhList = new ArrayList<>();

    public EnergyRegionSolarParksOutput() {

    }

    public List<Kwh> getKwhList() {
        return kwhList;
    }

    public void addKwh(Kwh kwh) {
        this.kwhList.add(kwh);
    }
}

package org.energygrid.east.simulationnuclearservice.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private final String name;
    private List<Kwh> kwhList = new ArrayList<>();
    private List<Kwh> totalKwhList = new ArrayList<>();

    public Scenario(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Kwh> getKwhList() {
        return kwhList;
    }

    public void addKwh(Kwh kwh) {
        this.kwhList.add(kwh);
    }

    public List<Kwh> getTotalKwhList() {
        return totalKwhList;
    }

    public void addTotalKwh(Kwh kwh) {
        this.totalKwhList.add(kwh);
    }
}
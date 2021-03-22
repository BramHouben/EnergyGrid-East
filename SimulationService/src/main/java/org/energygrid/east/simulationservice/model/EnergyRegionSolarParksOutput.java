package org.energygrid.east.simulationservice.model;

import java.util.List;
import java.util.UUID;

public class EnergyRegionSolarParksOutput
{
    private UUID id;
    private double totalKWHRegion;

    public EnergyRegionSolarParksOutput(){

    }

    public EnergyRegionSolarParksOutput(UUID id, double totalKWHRegion) {
        this.id = id;

        this.totalKWHRegion = totalKWHRegion;
    }


    public double getTotalKWHRegion() {
        return totalKWHRegion;
    }



    public void setTotalKWHRegion(double totalKWHRegion) {
        this.totalKWHRegion = totalKWHRegion;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}


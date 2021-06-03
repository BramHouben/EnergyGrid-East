package org.energygrid.east.websocketsservice.models;

import java.util.List;

public class SolarParkProductionDTO {

    private List<SolarParkProductionViewModel> solarParkProductions;

    public SolarParkProductionDTO() {
    }

    public SolarParkProductionDTO(List<SolarParkProductionViewModel> solarParkProductions) {
        this.solarParkProductions = solarParkProductions;
    }

    public List<SolarParkProductionViewModel> getSolarParkProductions() {
        return solarParkProductions;
    }

    public void setSolarParkProductions(List<SolarParkProductionViewModel> solarParkProductions) {
        this.solarParkProductions = solarParkProductions;
    }
}

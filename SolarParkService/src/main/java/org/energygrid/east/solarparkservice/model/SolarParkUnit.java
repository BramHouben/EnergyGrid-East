package org.energygrid.east.solarparkservice.model;

import java.util.UUID;

public class SolarParkUnit {

    private UUID solarPanelUnit;
    private boolean broken;

    public SolarParkUnit(UUID solarPanelUnit, boolean broken) {
        this.solarPanelUnit = solarPanelUnit;
        this.broken = broken;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public UUID getSolarPanelUnit() {
        return solarPanelUnit;
    }

    public void setSolarPanelUnit(UUID solarPanelUnit) {
        this.solarPanelUnit = solarPanelUnit;
    }
}

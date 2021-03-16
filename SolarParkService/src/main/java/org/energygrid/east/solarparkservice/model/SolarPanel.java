package org.energygrid.east.solarparkservice.model;

import java.util.UUID;

public class SolarPanel {

    private UUID solarPanelId;
    private boolean broken;

    public SolarPanel(UUID solarPanelId, boolean broken) {
        this.solarPanelId = solarPanelId;
        this.broken = broken;
    }

    public UUID getSolarPanelId() {
        return solarPanelId;
    }

    public void setSolarPanelId(UUID solarPanelId) {
        this.solarPanelId = solarPanelId;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}

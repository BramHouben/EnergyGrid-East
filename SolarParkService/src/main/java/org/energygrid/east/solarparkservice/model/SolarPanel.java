package org.energygrid.east.solarparkservice.model;

public class SolarPanel {

    private int solarPanelId;
    private boolean broken;

    public SolarPanel(int solarPanelId, boolean broken) {
        this.solarPanelId = solarPanelId;
        this.broken = broken;
    }

    public int getSolarPanelId() {
        return solarPanelId;
    }

    public void setSolarPanelId(int solarPanelId) {
        this.solarPanelId = solarPanelId;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}

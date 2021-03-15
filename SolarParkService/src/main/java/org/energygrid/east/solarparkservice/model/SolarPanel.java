package org.energygrid.east.solarparkservice.model;

public class SolarPanel {

    private int solarPanelId;
    private boolean broken;
    private double wpPerHour;

    public SolarPanel(int solarPanelId, boolean broken, double wpPerHour) {
        this.solarPanelId = solarPanelId;
        this.broken = broken;
        this.wpPerHour = wpPerHour;
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

    public double getWpPerHour() {
        return wpPerHour;
    }

    public void setWpPerHour(double wpPerHour) {
        this.wpPerHour = wpPerHour;
    }
}

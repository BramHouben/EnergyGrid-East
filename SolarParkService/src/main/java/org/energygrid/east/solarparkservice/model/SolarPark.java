package org.energygrid.east.solarparkservice.model;

import java.util.List;

public class SolarPark {

    private int solarParkId;
    private String solarParkName;
    private int countSonarPanels;
    private List<SolarPanel> solarPanels;

    public SolarPark(String solarParkName, int countSonarPanels, List<SolarPanel> solarPanels) {
        this.solarParkName = solarParkName;
        this.solarPanels = solarPanels;
        this.countSonarPanels = countSonarPanels;
    }

    public SolarPark(int solarParkId, String solarParkName, int countSonarPanels, List<SolarPanel> solarPanels) {
        this.solarParkId = solarParkId;
        this.solarParkName = solarParkName;
        this.solarPanels = solarPanels;
        this.countSonarPanels = countSonarPanels;
    }


    public void setSolarParkId(int solarParkId) {
        this.solarParkId = solarParkId;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public void setSolarParkName(String solarParkName) {
        this.solarParkName = solarParkName;
    }

    public List<SolarPanel> getSolarPanels() {
        return solarPanels;
    }

    public void setSolarPanels(List<SolarPanel> solarPanels) {
        this.solarPanels = solarPanels;
    }

    public int getCountSonarPanels() {
        return countSonarPanels;
    }

    public void setCountSonarPanels(int countSonarPanels) {
        this.countSonarPanels = countSonarPanels;
    }
}

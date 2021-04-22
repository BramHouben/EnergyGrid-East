package org.energygrid.east.solarparkservice.model.dto;

import org.springframework.data.geo.Point;

public class SimulationSolarDTO {

    private final String solarParkName;
    private final int countSonarPanels;
    private final Point coordinates;

    public SimulationSolarDTO(String solarParkName, int countSonarPanels, Point coordinates) {
        this.solarParkName = solarParkName;
        this.countSonarPanels = countSonarPanels;
        this.coordinates = coordinates;
    }

    public String getSolarParkName() {
        return solarParkName;
    }

    public int getCountSonarPanels() {
        return countSonarPanels;
    }

    public Point getCoordinates() {
        return coordinates;
    }
}

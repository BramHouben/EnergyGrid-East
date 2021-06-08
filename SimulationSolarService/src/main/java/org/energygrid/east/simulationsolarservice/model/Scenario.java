package org.energygrid.east.simulationsolarservice.model;

import org.energygrid.east.simulationsolarservice.model.enums.ScenarioType;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.springframework.data.geo.Point;

public class Scenario {

    private String name;
    private ScenarioType scenarioType;
    private int amount;
    private SolarPanelType solarPanelType;
    private Point coordinates;
    private SolarPark solarPark;
    private SolarUnit solarUnit;
    private String turnOffTimes;
    private String description;

    public Scenario() {
        //Supposed to be empty
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public void setScenarioType(ScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }

    public SolarPanelType getSolarPanelType() {
        return solarPanelType;
    }

    public void setSolarPanelType(SolarPanelType solarPanelType) {
        this.solarPanelType = solarPanelType;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public SolarPark getSolarPark() { return solarPark; }

    public void setSolarPark(SolarPark solarPark) { this.solarPark = solarPark; }

    public SolarUnit getSolarUnit() {
        return solarUnit;
    }

    public void setSolarUnit(SolarUnit solarUnit) {
        this.solarUnit = solarUnit;
    }

    public String getTurnOffTimes() {
        return turnOffTimes;
    }

    public void setTurnOffTimes(String turnOffTimes) {
        this.turnOffTimes = turnOffTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package org.energygrid.east.simulationsolarservice.model;

import org.energygrid.east.simulationsolarservice.model.enums.ScenarioType;
import org.energygrid.east.simulationsolarservice.model.enums.SolarPanelType;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String name;
    private ScenarioType scenarioType;
    private String amount;
    private SolarPanelType solarPanelType;
    private Point coordinates;
    private SolarUnit solarUnit;
    private List<String> turnOffTimes;
    private String description;

    public Scenario() {
        turnOffTimes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScenarioType getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(ScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public SolarUnit getSolarUnit() {
        return solarUnit;
    }

    public void setSolarUnit(SolarUnit solarUnit) {
        this.solarUnit = solarUnit;
    }

    public List<String> getTurnOffTimes() {
        return turnOffTimes;
    }

    public void setTurnOffTimes(List<String> turnOffTimes) {
        this.turnOffTimes = turnOffTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

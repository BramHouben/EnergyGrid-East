package org.energygrid.east.simulationwindservice.model;

import org.energygrid.east.simulationwindservice.model.enums.EScenarioType;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String name;
    private EScenarioType scenarioType;
    private String amount;
    private Double type;
    private Point coordinates;
    private WindTurbine windTurbine;
    private List<String> windTurbineOffTimes;
    private String description;

    public Scenario() {
        windTurbineOffTimes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EScenarioType getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(EScenarioType scenarioType) {
        this.scenarioType = scenarioType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Double getType() {
        return type;
    }

    public void setType(Double type) {
        this.type = type;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public WindTurbine getWindTurbine() {
        return windTurbine;
    }

    public void setWindTurbine(WindTurbine windTurbine) {
        this.windTurbine = windTurbine;
    }

    public List<String> getWindTurbineOffTimes() {
        return windTurbineOffTimes;
    }

    public void setWindTurbineOffTimes(List<String> windTurbineOffTimes) {
        this.windTurbineOffTimes = windTurbineOffTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
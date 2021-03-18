package org.energygrid.east.weatherservice.models;

public class Weather {

    private String location;
    private double temperature;
    private String symbol;

    public Weather(String location, double temperature, String symbol) {
        this.location = location;
        this.temperature = temperature;
        this.symbol = symbol;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }

    public String getSymbol() {
        return symbol;
    }
}

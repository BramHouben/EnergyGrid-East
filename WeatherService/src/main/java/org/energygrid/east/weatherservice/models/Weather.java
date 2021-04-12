package org.energygrid.east.weatherservice.models;

public class Weather {

    private double temperature;
    private String symbol;

    public Weather(double temperature, String symbol) {
        this.temperature = temperature;
        this.symbol = symbol;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getSymbol() {
        return symbol;
    }
}

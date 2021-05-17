package org.energygrid.east.weatherservice.models;

public class Weather {

    private double temperature;
    private String symbol;

    public Weather(double temperature, String symbol) {
        this.temperature = temperature;
        this.symbol = symbol;
    }

    public Weather() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

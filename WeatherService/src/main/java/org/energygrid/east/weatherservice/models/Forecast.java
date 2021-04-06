package org.energygrid.east.weatherservice.models;

import java.util.Date;

public class Forecast {
    private double minTemperature;
    private double maxTemperature;
    private String symbol;
    private String date;
    private double windSpeed;
    private int sunPercentage;

    public Forecast(double minTemperature, double maxTemperature, String symbol, String date, double windSpeed, int sunPercentage) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.symbol = symbol;
        this.date = date;
        this.windSpeed = windSpeed;
        this.sunPercentage = sunPercentage;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getSunPercentage() {
        return sunPercentage;
    }

    public void setSunPercentage(int sunPercentage) {
        this.sunPercentage = sunPercentage;
    }
}

package org.energygrid.east.websocketsservice.models;

public class SolarParkProductionViewModel {

    private String id;
    private SolarParkViewModel solarPark;
    private double todayProduction;
    private double yearProduction;

    public SolarParkProductionViewModel() {
    }

    public SolarParkProductionViewModel(String id, SolarParkViewModel solarPark, double todayProduction, double yearProduction) {
        this.id = id;
        this.solarPark = solarPark;
        this.todayProduction = todayProduction;
        this.yearProduction = yearProduction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SolarParkViewModel getSolarPark() {
        return solarPark;
    }

    public void setSolarPark(SolarParkViewModel solarPark) {
        this.solarPark = solarPark;
    }

    public double getTodayProduction() {
        return todayProduction;
    }

    public void setTodayProduction(double todayProduction) {
        this.todayProduction = todayProduction;
    }

    public double getYearProduction() {
        return yearProduction;
    }

    public void setYearProduction(double yearProduction) {
        this.yearProduction = yearProduction;
    }
}

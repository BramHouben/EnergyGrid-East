package org.energygrid.east.energybalanceservice.model;

public class EnergyUsagePerMinute {

    private String day;
    private double kwh;
    private double price;
    private int hour;

    public EnergyUsagePerMinute(String day, double kwh, double price, int hour) {
        this.day = day;
        this.kwh = kwh;
        this.price = price;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "{" +
                "day='" + day + '\'' +
                ", kwh=" + kwh +
                ", price=" + price +
                ", hour=" + hour +
                '}';
    }
}

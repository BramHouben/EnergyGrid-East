package org.energygrid.east.energybalanceservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "usage")
public class EnergyUsage implements Serializable {

    private String id;
    private String userId;
    private String day;
    private double kwh;
    private double price;
    private int hour;

    public EnergyUsage(String id, String userId, String day, double kwh, double price, int hour) {
        this.id = id;
        this.userId = userId;
        this.day = day;
        this.hour = hour;
        this.kwh = kwh;
        this.price = price;
    }

    public EnergyUsage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String dayId) {
        this.day = dayId;
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
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", day='" + day + '\'' +
                ", kwh=" + kwh +
                ", price=" + price +
                ", hour=" + hour +
                '}';
    }}

package org.energygrid.east.websocketsservice.models;

import java.io.Serializable;

public class EnergyBalanceDTO implements Serializable {

    private long consume;
    private long production;
    private double balance;
    private String time;

    public EnergyBalanceDTO() {
    }

    public EnergyBalanceDTO(long consume, long production, double balance, String time) {
        this.consume = consume;
        this.production = production;
        this.balance = balance;
        this.time = time;
    }

    public long getConsume() {
        return consume;
    }

    public void setConsume(long consume) {
        this.consume = consume;
    }

    public long getProduction() {
        return production;
    }

    public void setProduction(long production) {
        this.production = production;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "consume=" + consume +
                ", production=" + production +
                ", balance=" + balance +
                ", time='" + time + '\'' +
                '}';
    }
}

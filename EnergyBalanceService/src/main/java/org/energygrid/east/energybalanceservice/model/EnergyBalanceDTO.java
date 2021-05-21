package org.energygrid.east.energybalanceservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EnergyBalanceDTO implements Serializable {

    private long consume;
    private long production;
    private double balance;
    private LocalDateTime time;

    public EnergyBalanceDTO() {}

    public EnergyBalanceDTO(long consume, long production, double balance, LocalDateTime time) {
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "EnergyBalanceDTO{" +
                "consume=" + consume +
                ", production=" + production +
                ", balance=" + balance +
                ", time=" + time +
                '}';
    }
}

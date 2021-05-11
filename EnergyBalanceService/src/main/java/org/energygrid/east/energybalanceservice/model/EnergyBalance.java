package org.energygrid.east.energybalanceservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "details")
public class EnergyBalance {
    @Id
    private UUID uuid;
    private long consume;
    private long production;
    private double balance;
    private LocalDateTime time;

    public EnergyBalance(UUID uuid, long consume, long production, double balance, LocalDateTime time) {
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

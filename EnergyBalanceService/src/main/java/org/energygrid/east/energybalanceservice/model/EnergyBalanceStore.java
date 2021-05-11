package org.energygrid.east.energybalanceservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "store")
public class EnergyBalanceStore {
    @Id
    private UUID uuid;

    private LocalDateTime time;

    private long production;

    private Type type;

    public EnergyBalanceStore() {

    }

    public EnergyBalanceStore(UUID uuid, LocalDateTime time, long production, Type type) {
        this.uuid = uuid;
        this.time = time;
        this.production = production;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getProduction() {
        return production;
    }

    public void setProduction(long production) {
        this.production = production;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

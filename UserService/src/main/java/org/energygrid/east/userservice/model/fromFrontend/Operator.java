package org.energygrid.east.userservice.model.fromFrontend;

import java.util.UUID;

public class Operator {
    private UUID uuid;

    public Operator() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
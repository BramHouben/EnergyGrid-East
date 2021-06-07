package org.energygrid.east.userservice.model.fromfrontend;

import java.util.UUID;

public class Operator {
    private UUID uuid;

    public Operator() {
        //Supposed to be empty
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

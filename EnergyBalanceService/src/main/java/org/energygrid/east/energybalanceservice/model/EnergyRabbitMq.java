package org.energygrid.east.energybalanceservice.model;

import java.io.Serializable;

public class EnergyRabbitMq implements Serializable {


    private final Long Amount;

    public EnergyRabbitMq(Long amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "{" +
                "Amount:" + Amount +
                '}';
    }
}

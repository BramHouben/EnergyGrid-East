package org.energygrid.east.energybalanceservice.service;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;

public interface IEnergyService {
    /**
     * @return Latest balance of energy in the region
     */
    EnergyBalance getLatestBalance();
}

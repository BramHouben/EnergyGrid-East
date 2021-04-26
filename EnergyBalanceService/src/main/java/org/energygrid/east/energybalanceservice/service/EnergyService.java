package org.energygrid.east.energybalanceservice.service;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnergyService implements IEnergyService {

    @Autowired
    private EnergyBalanceRepo energyBalanceRepo;

    @Override
    public EnergyBalance getLatestBalance() {

        return energyBalanceRepo.findFirstByOrderByTimeDesc();

    }
}

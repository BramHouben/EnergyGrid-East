package org.energygrid.east.energybalanceservice.service;

import org.energygrid.east.energybalanceservice.model.EnergyBalance;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EnergyService implements IEnergyService {

    private static final java.util.logging.Logger logger = Logger.getLogger(EnergyService.class.getName());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private EnergyBalanceRepo energyBalanceRepo;

    @Override
    public EnergyBalance getLatestBalance() {
        logger.log(Level.INFO, "latest balance called");

        return energyBalanceRepo.findFirstByOrderByTimeDesc();

    }
}

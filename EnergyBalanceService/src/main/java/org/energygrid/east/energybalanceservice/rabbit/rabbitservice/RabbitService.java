package org.energygrid.east.energybalanceservice.rabbit.rabbitservice;

import org.energygrid.east.energybalanceservice.model.EnergyBalanceStore;
import org.energygrid.east.energybalanceservice.model.Type;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class RabbitService implements IRabbitService {

    @Autowired
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Override
    public void addLatestWind(String message) {
        var energyBalanceStore = new EnergyBalanceStore();
        energyBalanceStore.setUuid(UUID.randomUUID());
        energyBalanceStore.setType(Type.WIND);
        energyBalanceStore.setProduction(Long.parseLong(message));
        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
        energyBalanceStoreRepo.save(energyBalanceStore);

    }

    @Override
    public void addLatestSolar(String message) {
        var energyBalanceStore = new EnergyBalanceStore();
        energyBalanceStore.setUuid(UUID.randomUUID());
        energyBalanceStore.setType(Type.SOLAR);
        energyBalanceStore.setProduction(Long.parseLong(message));
        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
        energyBalanceStoreRepo.save(energyBalanceStore);
    }

    @Override
    public void addLatestNuclear(String message) {
        var energyBalanceStore = new EnergyBalanceStore();
        energyBalanceStore.setUuid(UUID.randomUUID());
        energyBalanceStore.setType(Type.NUCLEAR);
        energyBalanceStore.setProduction(Long.parseLong(message));
        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
        energyBalanceStoreRepo.save(energyBalanceStore);
    }
}

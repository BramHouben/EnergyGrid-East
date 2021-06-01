package org.energygrid.east.energybalanceservice.rabbit.rabbitservice;

import com.google.gson.Gson;
import org.energygrid.east.energybalanceservice.model.EnergyBalanceStore;
import org.energygrid.east.energybalanceservice.model.EnergyUsage;
import org.energygrid.east.energybalanceservice.model.Type;
import org.energygrid.east.energybalanceservice.repo.EnergyBalanceStoreRepo;
import org.energygrid.east.energybalanceservice.repo.EnergyUsageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class RabbitService implements IRabbitService {

    @Autowired
    private EnergyUsageRepo energyUsageRepo;

    @Autowired
    private EnergyBalanceStoreRepo energyBalanceStoreRepo;

    @Override
    public void addLatestWind(double message) {
        var energyBalanceStore = new EnergyBalanceStore();
        energyBalanceStore.setUuid(UUID.randomUUID());
        energyBalanceStore.setType(Type.WIND);
        energyBalanceStore.setProduction((long) message);
        energyBalanceStore.setTime(LocalDateTime.now(ZoneOffset.UTC));
        energyBalanceStoreRepo.save(energyBalanceStore);

    }

    @Override
    public void addLatestSolar(double message) {
        var energyBalanceStore = new EnergyBalanceStore();
        energyBalanceStore.setUuid(UUID.randomUUID());
        energyBalanceStore.setType(Type.SOLAR);
        var newBalance10Minutes = (long) message / 10;
        energyBalanceStore.setProduction(newBalance10Minutes);
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

    @Override
    public void addLatestUsage(String message) {
        var gson = new Gson();
        var energyUsagePerHour = gson.fromJson(message, EnergyUsage.class);
        energyUsagePerHour.setKwh(energyUsagePerHour.getKwh() / 60);
        energyUsageRepo.save(energyUsagePerHour);


    }
}

package com.enerygrid.east.energyusageservice.service;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;

import java.util.List;

public interface IEnergyUsageService {
    List<EnergyUsage> getEnergyUsageOfUser(String userId, String date);

    List<EnergyUsage> generateHourlyUsage(String userId, String date);
}

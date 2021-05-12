package com.enerygrid.east.energyusageservice.service;

import com.enerygrid.east.energyusageservice.entity.EnergyUsage;
import com.enerygrid.east.energyusageservice.repository.EnergyUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnergyUsageService implements IEnergyUsageService {

    @Autowired
    private EnergyUsageRepository energyUsageRepository;

    @Override
    public List<EnergyUsage> getEnergyUsageOfUser(String userId, String day) {

        List<EnergyUsage> dailyUsage = energyUsageRepository.findUsageByUserId(userId, "1");

        if (dailyUsage.size() == 0) dailyUsage = generateHourlyUsage(userId, day);

        return dailyUsage;
    }

    public List<EnergyUsage> generateHourlyUsage(String userId, String day){
        List<EnergyUsage> dailyUsage = new ArrayList<>();

        for (int i = 1; i <= 24; i++){

            int kwh = getRandomKwh(i);
            double price = getKwhPrice(kwh);
            double kwhDouble = (double) kwh / 100;
            EnergyUsage usage = new EnergyUsage(UUID.randomUUID().toString(), userId, day, kwhDouble , price , i);

            energyUsageRepository.insert(usage);
            dailyUsage.add(usage);
        }

        double totalPrice = 0.0;
        double totalKwh = 0.0;

        for (EnergyUsage usage : dailyUsage) {
            totalPrice = totalPrice + usage.getPrice();
            totalKwh = totalKwh + usage.getKwh();
        }

        return dailyUsage;
    }

    private int getRandomKwh(int hour) {
        SecureRandom random = new SecureRandom();

        switch (hour) {
            case 1: case 2: case 3: case 4: case 5: case 6:
                return random.nextInt((29 - 25) + 1) + 25;
            case 24: case 7: case 8: case 9: case 10: case 21: case 22: case 23:
                return random.nextInt((34 - 30) + 1) + 30;
            case 11: case 13: case 14: case 15: case 16:
                return random.nextInt((39 - 35) + 1) + 35;
            case 12: case 20:
                return random.nextInt((46 - 40) + 1) + 40;
            case 17: case 19:
                return random.nextInt((54 - 47) + 1) + 47;
            case 18:
                return random.nextInt((60 - 55) + 1) + 55;
            default:
                return 0;
        }
    }

    private double getKwhPrice(int kwh){
        return round(0.22 / 100 * kwh);
    }

    private double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

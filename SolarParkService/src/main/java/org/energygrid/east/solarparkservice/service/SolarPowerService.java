package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.errormessages.CantAddSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.CantRemoveSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarPanel;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.repo.ISolarParkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SolarPowerService implements ISolarParkPower {

    @Autowired
    private ISolarParkRepo solarParkRepo;

    @Override
    public SolarPark getSolarParkByName(String name) {
        //todo use repo
        SolarPark solarPark = solarParkRepo.getSolarParkBySolarParkName(name);
        if (solarPark == null) {
            throw new SolarParkNotFoundException();
        }

        return solarPark;
    }

    @Override
    public boolean doesNameExist(String name) {
        // for now simpe implementation just return true with 1 else false.
        //todo use repo
        if (solarParkRepo.existsBySolarParkName(name)) {
            return true;
        } else {
            throw new SolarParkNotFoundException();
        }
    }

    @Override
    public void addSolarPark(int totalSonarPanels, String name) {
        //todo use repo
        if (name == null || totalSonarPanels == 0) throw new CantAddSolarParkException();
        SecureRandom random = new SecureRandom();
        List<SolarPanel> solarPanels = new ArrayList<>();
        for (int i = 0; i < totalSonarPanels; i++) {
            boolean isBroken = random.nextBoolean();
            SolarPanel solarPanel = new SolarPanel(UUID.randomUUID(), isBroken);
            solarPanels.add(solarPanel);
        }

        SolarPark solarPark = new SolarPark(UUID.randomUUID(), name, totalSonarPanels, solarPanels);
        solarParkRepo.insert(solarPark);
    }

    @Override
    public void removeSolarPark(String name) {
        //todo check if exist
        if (!solarParkRepo.existsBySolarParkName(name)) {
            throw new CantRemoveSolarParkException();
        }

        solarParkRepo.removeBySolarParkName(name);

    }

    @Override
    public void updateSolarPark(UUID id, String name, int solarPanels) {
        SolarPark solarPark = solarParkRepo.getSolarParkBySolarParkId(id);
        solarPark.setCountSonarPanels(solarPanels);
        solarPark.setSolarParkName(name);
        solarParkRepo.save(solarPark);
    }
}

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
        SolarPark solarPark = solarParkRepo.getSolarParkBySolarParkName(name);
        if (solarPark == null) {
            throw new SolarParkNotFoundException();
        }

        return solarPark;
    }

    @Override
    public boolean doesNameExist(String name) {

        if (solarParkRepo.existsBySolarParkName(name)) {
            return true;
        } else {
            throw new SolarParkNotFoundException();
        }
    }

    @Override
    public void addSolarPark(SolarPark solarPark) {

        if (solarPark.getSolarParkName() == null || solarPark.getCountSonarPanels() == 0) throw new CantAddSolarParkException();

        List<SolarPanel> solarPanels = new ArrayList<>();
        for (int i = 0; i < solarPark.getCountSonarPanels(); i++) {

            SolarPanel solarPanel = new SolarPanel(UUID.randomUUID(), false);
            solarPanels.add(solarPanel);
        }
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setSolarPanels(solarPanels);
        solarParkRepo.insert(solarPark);
    }


    @Override
    public void removeSolarPark(String name) {
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

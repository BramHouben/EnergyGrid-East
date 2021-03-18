package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.errormessages.CantAddSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.CantRemoveSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarPanel;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.model.dto.AddSolarParkDTO;
import org.energygrid.east.solarparkservice.repo.ISolarParkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addSolarPark(AddSolarParkDTO solarParkDto) {

        if (solarParkDto.getSolarParkName() == null || solarParkDto.getCountSonarPanels() == 0) {
            throw new CantAddSolarParkException();
        }

        SolarPark solarPark = setDtoToObject(solarParkDto);

        solarParkRepo.insert(solarPark);
    }

    private SolarPark setDtoToObject(AddSolarParkDTO solarParkDto) {
        SolarPark solarPark = new SolarPark();
        solarPark.setSolarParkName(solarParkDto.getSolarParkName());
        solarPark.setCoordinates(solarParkDto.getCoordinates());
        solarPark.setPower(solarParkDto.getPower());
        solarPark.setApplicant(solarParkDto.getApplicant());
        solarPark.setCountSonarPanels(solarParkDto.getCountSonarPanels());
        solarPark.setProvince(solarParkDto.getProvince());
        solarPark.setYearOfRealised(solarParkDto.getYearOfRealised());
        solarPark.setZipCode(solarParkDto.getZipCode());
        solarPark.setMax(solarParkDto.getMax());
        solarPark.setSolarParkId(UUID.randomUUID());
        solarPark.setSolarPanels(makeSolarPanelsForList(solarParkDto.getCountSonarPanels()));
        return solarPark;
    }

    private List<SolarPanel> makeSolarPanelsForList(int count) {
        List<SolarPanel> solarPanels = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            SolarPanel solarPanel = new SolarPanel(UUID.randomUUID(), false);
            solarPanels.add(solarPanel);
        }
        return solarPanels;
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

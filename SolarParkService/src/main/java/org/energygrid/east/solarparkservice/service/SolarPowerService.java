package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.errormessages.CantAddSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.CantRemoveSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarParkUnit;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.energygrid.east.solarparkservice.model.dto.AddSolarParkDTO;
import org.energygrid.east.solarparkservice.repo.ISolarParkRepo;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configurable(autowire = Autowire.BY_TYPE)
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

        if (solarParkDto.getSolarParkName() == null || solarParkDto.getCountSonarPanels() == 0|| solarParkDto.getCoordinates() == null) {
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
        solarPark.setUnits(makeSolarParkUnits(solarParkDto.getCountSonarPanels()));
        return solarPark;
    }

    private List<SolarParkUnit> makeSolarParkUnits(double count) {
        double TotalSonarUnits = count/20;

        List<SolarParkUnit> solarParkUnits = new ArrayList<>();
        for (int i = 0; i <=TotalSonarUnits; i++) {

            SolarParkUnit solarParkUnit = new SolarParkUnit(UUID.randomUUID(), false);
            solarParkUnits.add(solarParkUnit);
        }
        return solarParkUnits;
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

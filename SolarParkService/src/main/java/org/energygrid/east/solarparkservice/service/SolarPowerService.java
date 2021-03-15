package org.energygrid.east.solarparkservice.service;

import org.energygrid.east.solarparkservice.errormessages.CantAddSolarParkException;
import org.energygrid.east.solarparkservice.errormessages.SolarParkNotFoundException;
import org.energygrid.east.solarparkservice.model.SolarPanel;
import org.energygrid.east.solarparkservice.model.SolarPark;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolarPowerService implements ISolarParkPower {

    private final List<SolarPanel> solarPanels;
    private final List<SolarPark> solarParks;

    public SolarPowerService() {
        this.solarPanels = new ArrayList<>();
        this.solarParks = new ArrayList<>();
        SolarPanel solarPanel = new SolarPanel(1, false);
        solarPanels.add(solarPanel);
        SolarPark solarPark = new SolarPark(0, "firstSolarpark", 300, solarPanels);
        solarParks.add(solarPark);
    }

    @Override
    public SolarPark getSolarParkById(int id) {
        //todo use repo
        SolarPark solarPark = solarParks.stream().findAny().orElseThrow(() -> new SolarParkNotFoundException(id));


        return solarPark;
    }

    @Override
    public boolean doesIdExist(int id) {
        // for now simpe implementation just return true with 1 else false.
        //todo use repo
        if (id == 1) {
            return true;
        } else {
            throw new SolarParkNotFoundException(id);
        }
    }

    @Override
    public void addSolarPark(int totalSonarPanels, String name) {
        //todo use repo
        if (name == null || totalSonarPanels == 0) throw new CantAddSolarParkException();
        SolarPark solarPark = new SolarPark("newSolarPark", 300, solarPanels);
        solarParks.add(solarPark);
    }

    @Override
    public void removeSolarPark(String name) {
        //todo use repo

        SolarPark solarPark = solarParks.get(0);

        solarParks.remove(solarPark);
    }

    @Override
    public void updateSolarPark(int id, String name, int solarpanels) {
        SolarPark solarPark = solarParks.get(id);
        solarPark.setCountSonarPanels(solarpanels);
        solarPark.setSolarParkName(name);

    }
}

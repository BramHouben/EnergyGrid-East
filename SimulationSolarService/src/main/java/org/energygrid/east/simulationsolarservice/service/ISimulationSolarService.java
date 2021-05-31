package org.energygrid.east.simulationsolarservice.service;

import org.energygrid.east.simulationsolarservice.model.SolarParkProduction;

import java.util.List;

public interface ISimulationSolarService {

    /**
     * Get an overview of all solar parks
     * In this overview you can see the daily and annual production
     * @return List of solar park productions
     */
    List<SolarParkProduction> getOverviewProductionSolarParks();
}

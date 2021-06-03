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

    /**
     * Get a number of the total production this year
     * @return A number of annual production
     */
    Double getYearProduction();

    /**
     * Get a number of the total production of today
     * @return A number of todays production
     */
    Double getTodayProduction();
}

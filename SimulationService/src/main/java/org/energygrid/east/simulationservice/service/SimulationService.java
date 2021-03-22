package org.energygrid.east.simulationservice.service;

import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksInput;
import org.energygrid.east.simulationservice.model.EnergyRegionSolarParksOutput;
import org.energygrid.east.simulationservice.model.Simulation;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SimulationService implements ISimulation {

    private final List<Simulation> simulations;

    public SimulationService() {
        simulations = new ArrayList<>();
    }

    @Override
    public Simulation getSimulationById(String id) {
        return simulations.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void addSimulation(Simulation simulation) {
        simulations.add(simulation);
    }

    @Override
    public void deleteSimulation(String id) {
        Simulation simulation = simulations.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        simulations.remove(simulation);
    }

    @Override
    public EnergyRegionSolarParksOutput simulationEnergyGrid(List<EnergyRegionSolarParksInput> energyRegionSolarParksInput) {

        //  List<EnergyRegionSolarParksOutput> energyRegionSolarParksOutputs = new ArrayList<>();

        double temperature = 25;
        double correctionFactor = 0.85;
        double energyPerSolarPanel = 0.27;
        EnergyRegionSolarParksOutput energyRegionSolarParksOutput = new EnergyRegionSolarParksOutput();
        energyRegionSolarParksOutput.setId(UUID.randomUUID());
        for (var energy : energyRegionSolarParksInput) {

            Point place = energy.getPlaceSolarPark();
            //todo roep percentage vanuit point
            double sunday1 = 24 * 0.4;
            double sunday2 = 24 * 0.3;
            double total = sunday1 + sunday2;

            for (int i = 0; i < total; i++) {

                double already = energyRegionSolarParksOutput.getTotalKWHRegion();
                double test = already + (energy.getTotalCountSolarPanels() * energyPerSolarPanel);
                energyRegionSolarParksOutput.setTotalKWHRegion(test);

            }
        }
        return energyRegionSolarParksOutput;
    }
}

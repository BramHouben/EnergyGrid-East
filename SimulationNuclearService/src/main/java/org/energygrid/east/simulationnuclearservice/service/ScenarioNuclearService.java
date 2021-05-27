package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Kwh;
import org.energygrid.east.simulationnuclearservice.model.Scenario;
import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.repository.ScenarioNuclearRepository;
import org.energygrid.east.simulationnuclearservice.repository.SimulationNuclearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScenarioNuclearService implements IScenarioNuclearService {

    @Autowired
    private SimulationNuclearRepository simulationNuclearRepository;

    @Autowired
    private ScenarioNuclearRepository scenarioNuclearRepository;

    @Override
    public Scenario createScenario(ScenarioDTO scenarioDTO) {
        var scenario = new Scenario(scenarioDTO.getName());

        switch (scenarioDTO.getScenarioType()) {
            case SHUTOFF_REACTOR:
                scenario = createScenarioPowerplantShutoff(scenarioDTO, scenario);
                scenarioNuclearRepository.save(scenario);
                return scenario;
            case ADD_REACTOR:
                scenario = createScenarioAddReactor(scenarioDTO, scenario);
                scenarioNuclearRepository.save(scenario);
                return scenario;
            case REMOVE_REACTOR:
                scenario = createScenarioRemoveReactor(scenarioDTO, scenario);
                scenarioNuclearRepository.save(scenario);
                return scenario;
        }
        return scenario;
    }

    private Scenario createScenarioPowerplantShutoff(ScenarioDTO scenarioDTO, Scenario scenario) {
        var simulation = simulationNuclearRepository.getSimulationBySimulationId(scenarioDTO.getId());
        var time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {
            if (time.isEqual(scenarioDTO.getStartTimeEvent())) {
                for (int j = 0; j < scenarioDTO.getHours(); j++) {
                    scenario.addKwh(new Kwh(0, time));
                    scenario.addTotalKwh(calculateTotalKwh(simulation, 0, time));
                    time = time.plusHours(1);
                    i++;
                }
                continue;
            }
            scenario.addKwh(new Kwh(simulation.getMaxPower(), time));
            scenario.addTotalKwh(calculateTotalKwh(simulation, simulation.getMaxPower(), time));
            time = time.plusHours(1);
        }
        return scenario;
    }

    private Scenario createScenarioAddReactor(ScenarioDTO scenarioDTO, Scenario scenario) {
        List<Simulation> simulations = simulationNuclearRepository.findAll();

        int kw = 0;

        for (Simulation simulation : simulations) {
            kw += simulation.getMaxPower();
        }

        LocalDateTime time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {
            int correctedKw = kw;

            if (time.isEqual(scenarioDTO.getStartTimeEvent()) || time.isAfter(scenarioDTO.getStartTimeEvent())) {
                correctedKw += scenarioDTO.getPower();

                scenario.addKwh(new Kwh(scenarioDTO.getPower(), time));
                scenario.addTotalKwh(new Kwh(correctedKw, time));

                continue;
            }

            scenario.addKwh(new Kwh(0, time));
            scenario.addTotalKwh(new Kwh(correctedKw, time));
        }
        return scenario;
    }

    private Scenario createScenarioRemoveReactor(ScenarioDTO scenarioDTO, Scenario scenario) {
        List<Simulation> simulations = simulationNuclearRepository.findAll();
        var simulation = simulationNuclearRepository.getSimulationBySimulationId(scenarioDTO.getId());
        simulations.remove(simulation);

        int kw = 0;

        for (Simulation simulationFromList : simulations) {
            kw += simulationFromList.getMaxPower();
        }

        LocalDateTime time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {

            if (time.isEqual(scenarioDTO.getStartTimeEvent()) || time.isAfter(scenarioDTO.getStartTimeEvent())) {
                scenario.addKwh(new Kwh(simulation.getMaxPower(), time));
                scenario.addTotalKwh(new Kwh(kw, time));
                continue;
            }

            scenario.addKwh(new Kwh(simulation.getMaxPower(), time));
            scenario.addTotalKwh(new Kwh((double)kw + simulation.getMaxPower(), time));
        }
        return scenario;
    }

    private Kwh calculateTotalKwh(Simulation simulation, int kilowatt, LocalDateTime time) {
        List<Simulation> simulationList = simulationNuclearRepository.findAll();
        simulationList.remove(simulation);

        int kw = 0;

        for (Simulation simulationFromList : simulationList) {
            kw += simulationFromList.getMaxPower();
        }

        kw += kilowatt;
        return new Kwh(kw, time);
    }


    @Override
    public List<Scenario> getScenarios() {
        return scenarioNuclearRepository.findAll();
    }
}
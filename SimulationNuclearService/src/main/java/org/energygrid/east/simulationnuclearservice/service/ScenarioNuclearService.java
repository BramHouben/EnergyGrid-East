package org.energygrid.east.simulationnuclearservice.service;

import org.energygrid.east.simulationnuclearservice.model.Kwh;
import org.energygrid.east.simulationnuclearservice.model.ProductionExpectation;
import org.energygrid.east.simulationnuclearservice.model.Simulation;
import org.energygrid.east.simulationnuclearservice.model.dto.ScenarioDTO;
import org.energygrid.east.simulationnuclearservice.model.results.ScenarioExpectationResult;
import org.energygrid.east.simulationnuclearservice.model.results.SimulationExpectationResult;
import org.energygrid.east.simulationnuclearservice.model.results.SimulationResult;
import org.energygrid.east.simulationnuclearservice.repository.ScenarioNuclearRepository;
import org.energygrid.east.simulationnuclearservice.repository.SimulationNuclearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ScenarioNuclearService implements IScenarioNuclearService {

    @Autowired
    private SimulationNuclearRepository simulationNuclearRepository;

    @Autowired
    private ScenarioNuclearRepository scenarioNuclearRepository;

    @Override
    public ScenarioExpectationResult createScenario(ScenarioDTO scenarioDTO) {
        var scenarioExpectationResult = new ScenarioExpectationResult(UUID.randomUUID().toString(), scenarioDTO.getName(), scenarioDTO.getScenarioType(), LocalDateTime.now().toString(), new SimulationExpectationResult(), "Nuclear Scenario", new Point(20, 20));

        switch (scenarioDTO.getScenarioType()) {
            case SHUTOFF_REACTOR:
                scenarioExpectationResult = createScenarioPowerplantShutoff(scenarioDTO, scenarioExpectationResult);
                scenarioNuclearRepository.save(scenarioExpectationResult);
                return scenarioExpectationResult;
            case ADD_REACTOR:
                scenarioExpectationResult = createScenarioAddReactor(scenarioDTO, scenarioExpectationResult);
                scenarioNuclearRepository.save(scenarioExpectationResult);
                return scenarioExpectationResult;
            case REMOVE_REACTOR:
                scenarioExpectationResult = createScenarioRemoveReactor(scenarioDTO, scenarioExpectationResult);
                scenarioNuclearRepository.save(scenarioExpectationResult);
                return scenarioExpectationResult;
        }
        return scenarioExpectationResult;
    }

    private ScenarioExpectationResult createScenarioPowerplantShutoff(ScenarioDTO scenarioDTO, ScenarioExpectationResult scenarioExpectationResult) {
        var simulation = simulationNuclearRepository.getSimulationBySimulationId(scenarioDTO.getId());

        var simulationResult = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
        var simulationResultObject = new SimulationResult();
        simulationResultObject.setName(simulation.getName());
        simulationResult.add(simulationResultObject);
        scenarioExpectationResult.getSimulationExpectationResult().setSimulationResults(simulationResult);

        scenarioExpectationResult.getSimulationExpectationResult().setKwTotalResult(0.0);
        scenarioExpectationResult.getSimulationExpectationResult().setSimulationId(simulation.getSimulationId().toString());
        scenarioExpectationResult.getSimulationExpectationResult().setCreatedAt(LocalDateTime.now().toString());

        var time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {
            if (time.isEqual(scenarioDTO.getStartTimeEvent())) {
                for (int j = 0; j < scenarioDTO.getHours(); j++) {
                    var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
                    simulationResults.get(0).addProductionExpectation(new ProductionExpectation(0, time));
                    time = time.plusHours(1);
                    i++;
                }
                continue;
            }
            var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
            simulationResults.get(0).addProductionExpectation(new ProductionExpectation(simulation.getMaxPower(), time));

            var totalPower = scenarioExpectationResult.getSimulationExpectationResult().getKwTotalResult();
            scenarioExpectationResult.getSimulationExpectationResult().setKwTotalResult(totalPower += simulation.getMaxPower());
            time = time.plusHours(1);
        }
        return scenarioExpectationResult;
    }

    private ScenarioExpectationResult createScenarioAddReactor(ScenarioDTO scenarioDTO, ScenarioExpectationResult scenarioExpectationResult) {
        List<Simulation> simulations = simulationNuclearRepository.findAll();

        var simulationResult = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
        simulationResult.add(new SimulationResult());
        scenarioExpectationResult.getSimulationExpectationResult().setSimulationResults(simulationResult);

        int kw = 0;

        for (Simulation simulation : simulations) {
            kw += simulation.getMaxPower();
        }

        LocalDateTime time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {
            int correctedKw = kw;

            if (time.isEqual(scenarioDTO.getStartTimeEvent()) || time.isAfter(scenarioDTO.getStartTimeEvent())) {
                correctedKw += scenarioDTO.getPower();

                var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
                simulationResults.get(0).addProductionExpectation(new ProductionExpectation(scenarioDTO.getPower(), time));

                var totalPower = scenarioExpectationResult.getSimulationExpectationResult().getKwTotalResult();
                scenarioExpectationResult.getSimulationExpectationResult().setKwTotalResult(totalPower += scenarioDTO.getPower());

                continue;
            }

            var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
            simulationResults.get(0).addProductionExpectation(new ProductionExpectation(0, time));
        }
        return scenarioExpectationResult;
    }

    private ScenarioExpectationResult createScenarioRemoveReactor(ScenarioDTO scenarioDTO, ScenarioExpectationResult scenarioExpectationResult) {
        List<Simulation> simulations = simulationNuclearRepository.findAll();

        var simulationResult = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
        simulationResult.add(new SimulationResult());
        scenarioExpectationResult.getSimulationExpectationResult().setSimulationResults(simulationResult);

        var simulation = simulationNuclearRepository.getSimulationBySimulationId(scenarioDTO.getId());
        simulations.remove(simulation);

        int kw = 0;

        for (Simulation simulationFromList : simulations) {
            kw += simulationFromList.getMaxPower();
        }

        LocalDateTime time = scenarioDTO.getStartTime();

        for (int i = 0; i < 48; i++) {

            if (time.isEqual(scenarioDTO.getStartTimeEvent()) || time.isAfter(scenarioDTO.getStartTimeEvent())) {
                var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
                simulationResults.get(0).addProductionExpectation(new ProductionExpectation(0, time));

                continue;
            }

            var simulationResults = scenarioExpectationResult.getSimulationExpectationResult().getSimulationResults();
            simulationResults.get(0).addProductionExpectation(new ProductionExpectation(scenarioDTO.getPower(), time));

            var totalPower = scenarioExpectationResult.getSimulationExpectationResult().getKwTotalResult();
            scenarioExpectationResult.getSimulationExpectationResult().setKwTotalResult(totalPower += scenarioDTO.getPower());
        }
        return scenarioExpectationResult;
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
    public List<ScenarioExpectationResult> getScenarios() {
        return scenarioNuclearRepository.findAll();
    }

    @Override
    public List<ScenarioExpectationResult> getLatestScenarios() {
        return scenarioNuclearRepository.findTop3ByOrderByCreatedAtDesc();
    }
}
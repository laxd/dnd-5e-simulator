package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    private final EncounterFactory encounterFactory;

    public Simulation(EncounterFactory encounterFactory) {
        this.encounterFactory = encounterFactory;
    }

    public void runSimulation(EncounterConfig config, int count) {
        for(int i=1; i<=count; i++) {
            Encounter encounter = encounterFactory.createEncounter(config);

            encounter.startEncounter();
            LOGGER.info("Completed simulation {}/{}", i, count);
        }
    }
}

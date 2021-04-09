package uk.laxd.dndSimulator.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEvent;
import uk.laxd.dndSimulator.event.EncounterEventType;
import uk.laxd.dndSimulator.event.EventOutcome;
import uk.laxd.dndSimulator.statistics.EncounterStatsExtractor;
import uk.laxd.dndSimulator.statistics.EncounterStatsFilter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A simulation consists of a encounter repeated a number of times, with
 * outcomes returned for statistical analysis
 */
public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    public void runSimulation(Encounter encounter, int count) {
        for(int i=1; i<=count; i++) {
            encounter.reset();
            encounter.startEncounter();
            LOGGER.info("Completed simulation {}/{}", i, count);
        }
    }
}

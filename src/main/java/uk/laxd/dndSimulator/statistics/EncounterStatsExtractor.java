package uk.laxd.dndSimulator.statistics;

import uk.laxd.dndSimulator.event.EncounterEvent;

public interface EncounterStatsExtractor {

    int getAttribute(EncounterEvent event);

}

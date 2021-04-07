package uk.laxd.dndSimulator.statistics;

import uk.laxd.dndSimulator.action.EncounterOutcome;
import uk.laxd.dndSimulator.event.EncounterEvent;

public interface EncounterStatsExtractor {

    public int getAttribute(EncounterEvent event);

}

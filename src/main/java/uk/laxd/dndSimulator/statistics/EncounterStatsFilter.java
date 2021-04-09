package uk.laxd.dndSimulator.statistics;

import uk.laxd.dndSimulator.event.EncounterEvent;

public interface EncounterStatsFilter {

    boolean matches(EncounterEvent event);

}

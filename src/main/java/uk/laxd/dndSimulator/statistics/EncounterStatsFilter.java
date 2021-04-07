package uk.laxd.dndSimulator.statistics;

import uk.laxd.dndSimulator.event.EncounterEvent;

public interface EncounterStatsFilter {

    public boolean matches(EncounterEvent event);

}

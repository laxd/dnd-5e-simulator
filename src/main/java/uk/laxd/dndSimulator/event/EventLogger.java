package uk.laxd.dndSimulator.event;

import java.util.Collection;

public interface EventLogger {

    void logEvent(EncounterEvent event);
    void logEvent(EncounterEventType eventType);
    Collection<EncounterEvent> getEvents();

}

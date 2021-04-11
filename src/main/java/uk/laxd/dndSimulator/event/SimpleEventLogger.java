package uk.laxd.dndSimulator.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Scope("singleton")
public class SimpleEventLogger implements EventLogger {

    private int index = 1;

    private Collection<EncounterEvent> events = new ArrayList<>();

    public void logEvent(EncounterEventType encounterEventType) {
        EncounterEvent event = new EncounterEvent();
        event.type = encounterEventType;

        logEvent(event);
    }

    public void logEvent(EncounterEvent event) {
        // Set the index.
        event.setIndex(index);

        this.events.add(event);

        index++;
    }

    public Collection<EncounterEvent> getEvents() {
        return events;
    }

}

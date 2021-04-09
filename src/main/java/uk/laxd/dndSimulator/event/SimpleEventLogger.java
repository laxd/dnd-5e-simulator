package uk.laxd.dndSimulator.event;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class SimpleEventLogger implements EventLogger {

    private Collection<EncounterEvent> events = new ArrayList<>();

    public void logEvent(EncounterEvent event) {
        this.events.add(event);
    }

    public Collection<EncounterEvent> getEvents() {
        return events;
    }

}

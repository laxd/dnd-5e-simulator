package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EncounterOutcome {

    private Collection<Character> participants = new ArrayList<>();
    private Collection<EncounterEvent> events = new ArrayList<>();

    public void addParticipant(Character character) {
        this.participants.add(character);
    }

    public Collection<EncounterEvent> getEvents() {
        return events;
    }

    public void addEvents(Collection<EncounterEvent> events) {
        this.events.addAll(events);
    }

    public void addEvent(EncounterEvent event) {
        this.events.add(event);
    }

}

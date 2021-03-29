package uk.laxd.dndSimulator.action;

import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.event.EncounterEvent;

import java.util.ArrayList;
import java.util.Collection;

public class TurnOutcome {
    private boolean hit = false;
    private int damage = 0;
    private Character target = null;
    private Collection<EncounterEvent> events = new ArrayList<>();

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }

    public void addEvents(Collection<EncounterEvent> events) {
        this.events.addAll(events);
    }

    public Collection<EncounterEvent> getEvents() {
        return new ArrayList<>(events);
    }
}

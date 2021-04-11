package uk.laxd.dndSimulator.event;

import uk.laxd.dndSimulator.Effect;
import uk.laxd.dndSimulator.action.AttackOutcome;
import uk.laxd.dndSimulator.action.Damage;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.equipment.Weapon;

/**
 * A collection of EncounterEvents can be used to describe an entire encounter
 * and may contain:
 *
 * Attacks
 *
 */
// TODO: Subclass this with DamageEvent, HealingEvent, ItemUseEvent etc
public class EncounterEvent implements Comparable<EncounterEvent> {

    // Index of the event
    private int index;

    // What happened
    EncounterEventType type;

    // With what
    Weapon weapon;

    // How much
    Damage amount;

    // Why did it happen?
    Effect effect;

    // Who did it
    Character actor;

    // To who
    Character target;

    // Did it work? (TODO: Generalise)
    AttackOutcome eventOutcome;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EncounterEventType getType() {
        return type;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Damage getAmount() {
        return amount;
    }

    public Effect getEffect() {
        return effect;
    }

    public Character getActor() {
        return actor;
    }

    public Character getTarget() {
        return target;
    }

    public AttackOutcome getEventOutcome() {
        return eventOutcome;
    }

    @Override
    public String toString() {
        return String.format("%s attacked %s with %s dealing %s damage", actor, target, weapon, amount);
    }

    @Override
    public int compareTo(EncounterEvent encounterEvent) {
        return this.index - encounterEvent.index;
    }
}

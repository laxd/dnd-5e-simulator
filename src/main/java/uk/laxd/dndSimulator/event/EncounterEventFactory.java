package uk.laxd.dndSimulator.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.laxd.dndSimulator.action.AttackAction;
import uk.laxd.dndSimulator.action.Damage;
import uk.laxd.dndSimulator.action.Turn;
import uk.laxd.dndSimulator.character.Character;
import uk.laxd.dndSimulator.equipment.Weapon;

@Component
@Scope("Singleton")
public class EncounterEventFactory {

    private int count;

    public EncounterEvent createEncounterStartEvent() {
        EncounterEvent event = new EncounterEvent(count);
        event.type = EncounterEventType.ENCOUNTER_START;

        count++;

        return event;
    }

    public EncounterEvent createRoundStartEvent() {
        EncounterEvent event = new EncounterEvent(count);
        event.type = EncounterEventType.ROUND_START;

        count++;

        return event;
    }

    public EncounterEvent createTurnStartEvent(Turn turn) {
        EncounterEvent event = new EncounterEvent(count);
        event.type = EncounterEventType.TURN_START;
        event.actor = turn.getCharacter();

        count++;

        return event;
    }

    public EncounterEvent createNewMeleeAttackEvent(AttackAction attackAction) {
        EncounterEvent event = new EncounterEvent(count);
        event.actor = attackAction.getPerformer();
        event.target = attackAction.getTarget();
        event.amount = attackAction.getAttackDamage();
        event.weapon = attackAction.getWeapon();
        event.type = EncounterEventType.MELEE_ATTACK;
        event.eventOutcome = attackAction.getOutcome();

        count++;

        return event;
    }
}
